package com.bridgelabz.fundooApp.service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundooApp.dto.Email;
import com.bridgelabz.fundooApp.dto.NoteDto;
import com.bridgelabz.fundooApp.exception.NoteException;
import com.bridgelabz.fundooApp.exception.UserException;
import com.bridgelabz.fundooApp.model.Note;
import com.bridgelabz.fundooApp.model.User;
import com.bridgelabz.fundooApp.repository.NoteRepository;
import com.bridgelabz.fundooApp.repository.UserRepository;
import com.bridgelabz.fundooApp.utility.JWTTokenGenerator;
import com.bridgelabz.fundooApp.utility.MailUtil;
import com.bridgelabz.fundooApp.utility.RabbitMQSender;

@Service
public class NoteServiceImpl implements NoteService {

	@Autowired
	private JWTTokenGenerator tokenGenerator;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private RabbitMQSender rabbitMQMailSender;

	@Autowired
	private MailUtil mailutil;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	String Key = "userOne";

	@Override
	public String createNote(NoteDto noteDto, String token) {
		String userId = tokenGenerator.verifyToken(token);
		Optional<User> optUser = userRepository.findByUserId(userId);
		if (optUser.isPresent()) {
			System.out.println("user opt-->" + optUser.get());
			Note note = modelMapper.map(noteDto, Note.class);
			note.setCreationtTime(LocalDateTime.now());
			note.setUpdateTime(LocalDateTime.now());
			note.setUserId(userId);
			note = noteRepository.save(note);
//			userRepository.save(optUser.get());
			List<Note> demo = new ArrayList<Note>();
			demo.add(note);
			optUser.get().setUserNotes(demo);
			System.out.println("user opt-->" + optUser.get());
			userRepository.save(optUser.get());
			// return new Response(200, "note created ", null);
			return "note created";

		} else {
			throw new NoteException("note not created");
		}
	}

	@Override
	public String updateNote(NoteDto noteDto, String noteId, String token) {
		String userId = tokenGenerator.verifyToken(token);
		Optional<User> optUser = userRepository.findByUserId(userId);
		if (optUser.isPresent()) {
			Optional<Note> optNote = noteRepository.findById(noteId);
			if (optNote.isPresent()) {
				Note note = optNote.get();

				note.setUpdateTime(LocalDateTime.now());

				note.setTitle(noteDto.getTitle());
				note.setDescription(noteDto.getDescription());
				noteRepository.save(note);

				return "note updated";

			} else {
				// return new Response(202, "note doesnt exist", null);
				throw new NoteException("note doesnt exist");
			}
		} else {
			throw new UserException("User doesnt exist");
		}

	}

	@Override
	public String deleteNote(String noteId, String token) {
		String userId = tokenGenerator.verifyToken(token);
		Optional<User> optUser = userRepository.findByUserId(userId);
		return optUser.filter(user -> user != null).map(user -> {
			Optional<Note> optionalNote = noteRepository.findById(noteId);
			optionalNote.filter(note -> {
				return note.isTrash();
			}).map(note -> {
				noteRepository.delete(note);
				return "deleted note";
			}).orElseThrow(() -> new UserException("note not found"));
			return "deleted note";
		}).orElseThrow(() -> new UserException("note not found"));
	}

	@Override
	public Note getNote(String noteId, String token) {

		String userId = tokenGenerator.verifyToken(token);
		Optional<User> optUser = userRepository.findByUserId(userId);
		if (optUser.isPresent()) {
			Optional<Note> optNote = noteRepository.findById(noteId);
			if (optNote.isPresent()) {
				Note note = optNote.get();
				return note;
			} else {
				throw new NoteException("noteId doesnt match");
			}
		} else {
			throw new UserException("User is not present");
		}

	}

	@Override
	public List<Note> getAllNote(String token) {

		String userId = tokenGenerator.verifyToken(token);
//		List<Note> notes = noteRepository.findAll();
//		List<Note> filteredNotes = notes.stream().filter(note -> {
//			return note.getUserId().equals(userId);
//		}).collect(Collectors.toList());
//
//		return filteredNotes;

		// This gives us a single record
		return userRepository.findById(userId).get().getUserNotes();
	}

	@Override
	public List<Note> getTrash(String token) {
		String userId = tokenGenerator.verifyToken(token);
		List<Note> notes = noteRepository.findByUserId(userId);
		List<Note> noteslist = notes.stream().filter(data -> data.isTrash()).collect(Collectors.toList());
		return noteslist;
	}

	@Override
	public List<Note> getArchive(String token) {
		String userId = tokenGenerator.verifyToken(token);
		return noteRepository.findByUserIdAndIsArchive(userId, true);
	}

	@Override
	public String archiveAndUnarchive(String token, String noteId) {

		String userId = tokenGenerator.verifyToken(token);
		Optional<User> optUser = userRepository.findById(userId);
		if (optUser.isPresent()) {
			Optional<Note> optNote = noteRepository.findById(noteId);
			if (optNote.isPresent()) {
				Note note = optNote.get();
				if (note.isArchive()) {
					note.setArchive(false);
					noteRepository.save(note);
					return "note is unarchived";
				} else {

					note.setArchive(true);
					noteRepository.save(note);
					return "note is archived";
				}
			} else {
				throw new NoteException("note is not present");
			}
		} else {
			throw new UserException("User not present");
		}
	}

	@Override
	public String trashAndUntrash(String token, String noteId) {
		String userId = tokenGenerator.verifyToken(token);
		Optional<User> optUser = userRepository.findById(userId);
		if (optUser.isPresent()) {
			Optional<Note> optNote = noteRepository.findById(noteId);
			if (optNote.isPresent()) {
				Note note = optNote.get();
				if (note.isTrash()) {
					note.setTrash(false);
					noteRepository.save(note);
					return "note is untrash";
				} else {
					note.setTrash(true);
					noteRepository.save(note);
					return "note in trash";
				}
			} else {
				throw new NoteException("note is not present");
			}

		} else {
			throw new UserException("User doesnt exist");
		}
	}

	@Override
	public String pinAndUnpin(String token, String noteId) {
		String userId = tokenGenerator.verifyToken(token);
		Optional<User> optUser = userRepository.findById(userId);
		if (optUser.isPresent()) {
			Optional<Note> optNote = noteRepository.findById(noteId);
			if (optNote.isPresent()) {
				Note note = optNote.get();
				if (note.isPin()) {
					note.setPin(false);
					noteRepository.save(note);
					return "note is unpin";
				} else {
					note.setPin(true);
					noteRepository.save(note);
					return "note is pin";

				}
			} else {
				throw new NoteException("note doesnt exist");
			}

		} else {
			throw new UserException("User doesnt present");
		}
	}

	@Override
	public List<Note> sortByName(String token) {
		String userId = tokenGenerator.verifyToken(token);
		Optional<User> optUser = userRepository.findById(userId);
		List<Note> updateList = new ArrayList<Note>();
		if (optUser.isPresent()) {
			List<Note> noteList = noteRepository.findByUserId(userId);
			// List<Note> noteList = noteRepository.findAll();
			for (int i = 0; i < noteList.size(); i++) {
				for (int j = 0; j < noteList.size() - 1; j++) {
					if (noteList.get(i).getTitle().compareTo(noteList.get(j).getTitle()) < 0) {
						Note note = noteList.get(i);
						noteList.set(i, noteList.get(j));
						noteList.set(j, note);
					}
				}
			}
			return noteList;
		} else {
			throw new UserException("User not present");
		}

	}

	@Override
	public List<Note> sortByDate(String token) {

		String userId = tokenGenerator.verifyToken(token);
		Optional<User> optUser = userRepository.findById(userId);
		if (optUser.isPresent()) {
			List<Note> noteList = noteRepository.findByUserId(userId);
			// List<Note> noteList = noteRepository.findAll();
			for (int i = 0; i < noteList.size(); i++) {
				for (int j = 0; j < noteList.size(); j++) {
					if (noteList.get(i).getCreationtTime().compareTo(noteList.get(j).getCreationtTime()) < 0) {
						Note note = noteList.get(i);
						noteList.set(i, noteList.get(j));
						noteList.set(j, note);
					}
				}
			}
			return noteList;
		} else {
			throw new UserException("User not present ");
		}
	}

	// set note color
//	@Override
//	public String setColorToNote(String email, String noteId, String color) {
//		String token = (String) redisTemplate.opsForHash().get(Key, email);
//		String userId = tokenGenerator.verifyToken(token);
//		User user = userRepository.findById(userId).orElseThrow(() -> new UserException("user not exist"));
//		Note note = user.getUserNotes().stream().filter(data -> data.getNoteId() == noteId).findFirst()
//				.orElseThrow(() -> new NoteException("note not exist"));
//		note.setColor(color);
//		noteRepository.save(note);
//		return "note color is succesfully set...";
//	}

	// search note is part of elastic search so implement if you get free time
//	@Override
//	public Note findNoteFromUser(String email, String title, String description) {
//		String token = (String) redisTemplate.opsForHash().get(Key, email);
//		String userId = tokenGenerator.verifyToken(token);
//		User user = userRepository.findById(userId).orElseThrow(() -> new UserException("user not exist"));
//
//		Note note = user.getUserNotes().stream()
//				.filter(data -> data.getDescription().equals(description) && data.getTitle().equals(title)).findFirst()
//				.orElseThrow(() -> new NoteException("note not exist"));
//		return note;
//	}

	// set remainder to note
//	@Override
//	public String setReminderToNote(String email, String noteId, String time) {
//		String token = (String) redisTemplate.opsForHash().get(Key, email);
//		String userId = tokenGenerator.verifyToken(token);
//
//		User user = userRepository.findById(userId).orElseThrow(() -> new UserException("user not exist"));
//		Note note = user.getUserNotes().stream().filter(data -> data.getNoteId() == noteId).findFirst()
//				.orElseThrow(() -> new NoteException("note dosent exist"));
//
//		LocalDateTime localDateTime = LocalDateTime.now();
//		if (time.equalsIgnoreCase("tomorrow")) {
//			localDateTime = LocalDateTime.now().plusDays(1);
//		}
//		if (time.equalsIgnoreCase("weekly")) {
//			localDateTime = LocalDateTime.now().plusDays(7);
//		}
//		note.setReminder(localDateTime);
//		noteRepository.save(note);
//		return "Rminder set successfully to note";
//	}
//
//	@Override
//	public String closeReminder(String email, String noteId) {
//		String token = (String) redisTemplate.opsForHash().get(Key, email);
//		String userId = tokenGenerator.verifyToken(token);
//		User user = userRepository.findById(userId).orElseThrow(() -> new UserException("user not exist"));
//		Note note = user.getUserNotes().stream().filter(data -> data.getNoteId() == noteId).findFirst()
//				.orElseThrow(() -> new NoteException("note not exist"));
//		note.setReminder(null);
//		noteRepository.save(note);
//		return "reminder closed";
//	}

	@Override
	public String collaboratorNoteToUser(String email, String emailCollab, String noteId) {
		String token = (String) redisTemplate.opsForHash().get(Key, email);
		Email collaboratorEmail = new Email();
		String userId = tokenGenerator.verifyToken(token);

		User owner = userRepository.findById(userId).orElseThrow(() -> new UserException("user not present"));

		User user = userRepository.findAll().stream().filter(data -> data.getEmail().equals(emailCollab)).findFirst()
				.orElseThrow(() -> new UserException("collaborator user not exist"));

		Note note = owner.getUserNotes().stream().filter(data -> data.getNoteId().equals(noteId)).findFirst()
				.orElseThrow(() -> new UserException("note not found"));

		List<Note> alreadyCollabNotes = user.getCollaboratedNotes().stream()
				.filter(notes -> notes.getNoteId() == noteId)
				.collect(Collectors.toList());
		System.out.println("Note : "+note);
		
		if (alreadyCollabNotes != null) {
			throw new UserException("note already collaborated");
		} else {
//Finally adding note to user
			List<Note> collabList = new ArrayList<Note>();
			collabList.addAll(user.getCollaboratedNotes());
			collabList.add(note);
			user.setCollaboratedNotes(collabList);
//Addding user to note
			List<User> collabUser = new ArrayList<User>();
			collabUser.addAll(note.getCollaboratedUsers());
			collabUser.add(user);
			note.setCollaboratedUsers(collabUser);

			userRepository.save(user);
			noteRepository.save(note);

			collaboratorEmail.setTo(user.getEmail());
			collaboratorEmail.setSubject("Notify Note collaboration");
			collaboratorEmail.setBody("Note from " + owner.getEmail() + " collaborated to you\nTitle :"
					+ note.getTitle() + "\nDescription : " + note.getDescription());
			try {
				collaboratorEmail.setBody(mailutil.getLink("http://localhost:8086/userservice/", owner.getUserId()));
			} catch (IllegalArgumentException | UnsupportedEncodingException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			try {
				rabbitMQMailSender.sendMessageToQueue(collaboratorEmail);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return "Note collaborated successfully...";

	}
	@Override
	public String removeCollaboratorFromNote(String email, String collabEmailId, String noteId) {
		String token = (String) redisTemplate.opsForHash().get(Key, email);
		String userId = tokenGenerator.verifyToken(token);
		
		User owner = userRepository.findById(userId).orElseThrow(() -> new UserException("user not found"));
		
//		User user = userRepository.findById(noteId)
//				.orElseThrow(() -> new UserException("user not collabotarted"));
		
		User user = userRepository.findAll().stream().filter(data -> data.getEmail().equals(collabEmailId)).findFirst()
				.orElseThrow(() -> new UserException("collaborator user not exist"));
		
		System.out.println("Collab User: "+user.toString());

		List<Note> notes = owner.getUserNotes();
				System.out.println(notes);
		
		Note note = owner.getUserNotes().stream().filter(data -> data.getNoteId().equals(noteId)).findFirst()
				.orElseThrow(() -> new NoteException("note not exists"));
				
		user.getCollaboratedNotes().remove(note);
		List<Note> collabList = new ArrayList<Note>();
		collabList.remove(note);
		user.setCollaboratedNotes(collabList);
		
		note.getCollaboratedUsers().remove(user);

		userRepository.save(user);
		noteRepository.save(note);

		return "remove collaborator successfully";
	}
//
//	@Override
//	public List<Note> getAllCollaboratedNotes(String email) {
//		String token = (String) redisTemplate.opsForHash().get(Key, email);
//		String userId = tokenGenerator.verifyToken(token);
//		User user = userRepository.findById(userId).orElseThrow(() -> new UserException("user not exist"));
//		List<Note> collaboratedNotes = user.getCollaboratedNotes();
//		return collaboratedNotes;
//	}
//
//	@Override
//	public List<Note> getAllReminderNotes(String email) {
//		String token = (String) redisTemplate.opsForHash().get(Key, email);
//		String userId = tokenGenerator.verifyToken(token);
//		User user = userRepository.findById(userId).orElseThrow(() -> new UserException("user not exist"));
//		List<Note> reminderNotes = user.getUserNotes().stream().filter(data -> data.getReminder() != null)
//				.collect(Collectors.toList());
//		return reminderNotes;
//	}
//
//	@Override
//	public List<User> getAllCollaboratedOfUsers(String email, String noteId) {
//		String token = (String) redisTemplate.opsForHash().get(Key, email);
//		String userId = tokenGenerator.verifyToken(token);
//		User user = userRepository.findById(userId).orElseThrow(() -> new UserException("user not exist"));
//		Note note = noteRepository.findById(userId).orElseThrow(() -> new NoteException("note note present"));
//		System.out.println(note.toString());
//		List<User> collaboratedUsers = note.getCollaboratedUsers();
//		System.out.println("Collaborated users -> "+collaboratedUsers);
//		return collaboratedUsers;
//	}

}