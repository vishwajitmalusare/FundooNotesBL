package com.bridgelabz.fundooApp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundooApp.dto.NoteDto;
import com.bridgelabz.fundooApp.model.Note;
import com.bridgelabz.fundooApp.model.User;
import com.bridgelabz.fundooApp.response.Response;
import com.bridgelabz.fundooApp.service.NoteService;
import com.bridgelabz.fundooApp.service.UserService;
import com.bridgelabz.fundooApp.utility.ITokenGenerator;
import com.bridgelabz.fundooApp.utility.JWTTokenGenerator;

@CrossOrigin(origins = "*",allowedHeaders = {"*"}, exposedHeaders = {"email"})
@RestController
@RequestMapping("/note")
public class NoteController {

	@Autowired
	private NoteService noteService;
	
	@Autowired
	private JWTTokenGenerator tokenGenerator;

	@PostMapping("/createnote")
	public ResponseEntity<Response> createNote(@RequestBody NoteDto noteDto, @RequestParam String token)

	{
		String message = noteService.createNote(noteDto, token);
		Response response = new Response(HttpStatus.CREATED.value(), message, null);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@PutMapping("/updatenote")
	public ResponseEntity<Response> updateNote(@RequestBody NoteDto noteDto, @RequestParam String noteId,
			@RequestParam String token) {
		String message = noteService.updateNote(noteDto, noteId, token);
		Response response = new Response(HttpStatus.ACCEPTED.value(), message, null);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@DeleteMapping("/deletenote")
	public ResponseEntity<Response> deleteNote(@RequestParam String noteId, @RequestParam String token) {

		String message = noteService.deleteNote(noteId, token);
		Response response = new Response(HttpStatus.OK.value(), message, null);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@GetMapping("/getnote")
	public Note getNote(@RequestParam String noteId, @RequestParam String token) {
		Note note = noteService.getNote(noteId, token);
		return note;
	}

	@GetMapping("/getnotes")
	public List<Note> getAllNote(@RequestParam String token) {
		List<Note> noteList = noteService.getAllNote(token);
		return noteList;
	}

	@GetMapping("/gettrash")
	public List<Note> getTrash(@RequestParam String token) {
		List<Note> noteslist = noteService.getTrash(token);
		return noteslist;
	}

	@GetMapping("/getarchive")
	public List<Note> getArchive(@RequestParam String token) {
		List<Note> noteslist = noteService.getArchive(token);
		return noteslist;
	}

	@GetMapping("/archiveandunarchive")
	public ResponseEntity<Response> archiveUnarchiveNote(@RequestParam String token, @RequestParam String noteId) {
		String message = noteService.archiveAndUnarchive(token, noteId);
		Response response = new Response(HttpStatus.OK.value(), message, null);
		return new ResponseEntity<Response>(response, HttpStatus.OK);

	}

	@GetMapping("/trashanduntrash")
	public ResponseEntity<Response> trashAndUntrash(@RequestParam String token, @RequestParam String noteId) {
		String message = noteService.trashAndUntrash(token, noteId);
		Response response = new Response(HttpStatus.OK.value(), message, null);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@GetMapping("/pinandunpin")
	public ResponseEntity<Response> pinAndUnpin(@RequestParam String token, @RequestParam String noteId) {
		String message = noteService.pinAndUnpin(token, noteId);
		Response response = new Response(HttpStatus.OK.value(), message, null);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@GetMapping("/sortbyname")
	public List<Note> sortByName(@RequestParam String token) {
		List<Note> noteList = noteService.sortByName(token);
		return noteList;
	}

	@GetMapping("/sortbydate")
	public List<Note> sortByDate(@RequestParam String token) {
		List<Note> noteList = noteService.sortByDate(token);
		return noteList;
	}
	
	@PutMapping("/collaborateNote")
	public ResponseEntity<Response> addCollaborator(@RequestHeader String email, @RequestParam String collaboratorEmail, @RequestParam String noteId) {
		String message = noteService.collaboratorNoteToUser(email, collaboratorEmail, noteId);
		Response response = new Response(HttpStatus.OK.value(),message,null);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	
	@PutMapping("/removeCollaborator")
	public ResponseEntity<Response> removeCollaborator(@RequestHeader String email,@RequestParam String collabEmail,@RequestParam String noteId) {
		String message = noteService.removeCollaboratorFromNote(email, collabEmail, noteId);
		Response response =new Response(HttpStatus.OK.value(),message,null);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	
	@GetMapping("/getCollaboratedNote")
	public List<Note> getCollaboratedNotes(@RequestHeader String email) {
		 List<Note> collabNotes = noteService.getAllCollaboratedNotes(email);
		 return collabNotes;
	}
	
	@GetMapping("/getAllCollaboratedUsers")
	public List<User> getCollaboratedUsers(@RequestHeader String email, @RequestParam String noteId) {
		List<User> collboratedUsers = noteService.getAllCollaboratedUsers(email, noteId);
		return collboratedUsers;
	}

		@PutMapping("/setcolor")
	public ResponseEntity<Response> setColorToNote(@RequestHeader String email, @RequestParam String noteId, @RequestParam String color) {
		String message = noteService.setColorToNote(email, noteId, color);
		Response response = new Response(HttpStatus.OK.value(),message,null);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	
	@GetMapping("/findNote")
	public Note getNote(@RequestHeader String email, @RequestHeader String title, @RequestHeader String description) {
		Note note = noteService.findNoteFromUser(email, title, description);
		return note;
	} 
	
	//Its sohuld be put call or post
	@PutMapping("/setRemainder")
	public ResponseEntity<Response> setRemainderToNote(@RequestHeader String email,@RequestParam String noteId,@RequestParam String time) {
		String message = noteService.setReminderToNote(email, noteId, time);
		Response response = new Response(HttpStatus.OK.value(),message,null);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	
	@PutMapping("/removeReminder")
	public ResponseEntity<Response> deleteRemainderToNote(@RequestHeader String email, @RequestParam String noteId) {
		String message = noteService.closeReminder(email, noteId);
		Response res = new Response(HttpStatus.OK.value(),message,null);
		return new ResponseEntity<Response>(res,HttpStatus.OK);
	}

	

	
	@GetMapping("/getAllRemindersNotes")
	public List<Note> getRemindersNotes(@RequestHeader String email,@RequestParam String noteId){
		List<Note> reminderNotes = noteService.getAllReminderNotes(email,noteId);
		return reminderNotes;
	}
	

}