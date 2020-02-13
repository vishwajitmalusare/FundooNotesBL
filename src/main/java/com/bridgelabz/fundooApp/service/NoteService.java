package com.bridgelabz.fundooApp.service;

import java.util.List;

import com.bridgelabz.fundooApp.dto.NoteDto;
import com.bridgelabz.fundooApp.model.Note;
import com.bridgelabz.fundooApp.model.User;

public interface NoteService {

	String createNote(NoteDto noteDto, String token);

	String updateNote(NoteDto noteDto, String noteId, String token);

	String deleteNote(String noteId, String token);

	Note getNote(String noteId, String token);

	List<Note> getAllNote(String token);

	List<Note> getTrash(String token);

	List<Note> getArchive(String token);

	String archiveAndUnarchive(String token, String noteId);

	String trashAndUntrash(String token, String noteId);

	String pinAndUnpin(String token, String noteId);

	List<Note> sortByName(String token);

	List<Note> sortByDate(String token);
		
//	String setColorToNote(String email, String noteId, String color);
//	
//	Note findNoteFromUser(String email, String title, String description);
//	
//	String setReminderToNote(String email, String noteId, String time);
//	
//	String closeReminder(String email, String noteId);
//	
	String collaboratorNoteToUser(String email, String emailCollab, String noteId);
	
	String removeCollaboratorFromNote(String email, String collabEmailId, String noteId);
//	
//	List<Note> getAllCollaboratedNotes(String email);
//	
//	List<Note> getAllReminderNotes(String email);
	
//	List<User> getAllCollaboratedOfUsers(String email, String noteId);

}
