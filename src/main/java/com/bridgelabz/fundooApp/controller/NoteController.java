package com.bridgelabz.fundooApp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundooApp.dto.NoteDto;
import com.bridgelabz.fundooApp.model.Note;
import com.bridgelabz.fundooApp.response.Response;
import com.bridgelabz.fundooApp.service.NoteService;

@RestController
@RequestMapping("/noteservice")
public class NoteController {

	@Autowired
	private NoteService noteService;

	@PostMapping("/note")
	public ResponseEntity<Response> createNote(@RequestBody NoteDto noteDto, @RequestParam String token)

	{
		String message = noteService.createNote(noteDto, token);
		Response response = new Response(HttpStatus.OK.value(), message, null);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@PutMapping("/note")
	public ResponseEntity<Response> updateNote(@RequestBody NoteDto noteDto, @RequestParam String noteId,
			@RequestParam String token) {
		String message = noteService.updateNote(noteDto, noteId, token);
		Response response = new Response(HttpStatus.OK.value(), message, null);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@DeleteMapping("/note")
	public ResponseEntity<Response> deleteNote(@RequestParam String noteId, @RequestParam String token) {

		String message = noteService.deleteNote(noteId, token);
		Response response = new Response(HttpStatus.OK.value(), message, null);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@GetMapping("/note")
	public Note getNote(@RequestParam String noteId, @RequestParam String token) {
		Note note = noteService.getNote(noteId, token);
		return note;
	}

	@GetMapping("/notes")
	public List<Note> getAllNote(@RequestParam String token) {
		List<Note> noteList = noteService.getAllNote(token);
		return noteList;
	}

	@GetMapping("/getTrash")
	public List<Note> getTrash(@RequestParam String token) {
		List<Note> noteslist = noteService.getTrash(token);
		return noteslist;
	}

	@GetMapping("/getArchive")
	public List<Note> getArchive(@RequestParam String token) {
		List<Note> noteslist = noteService.getArchive(token);
		return noteslist;
	}

	@GetMapping("/archiveandUnarchive")
	public ResponseEntity<Response> archiveUnarchiveNote(@RequestParam String token, @RequestParam String noteId) {
		String message = noteService.archiveAndUnarchive(token, noteId);
		Response response = new Response(HttpStatus.OK.value(), message, null);
		return new ResponseEntity<Response>(response, HttpStatus.OK);

	}

	@GetMapping("/trashandUntrash")
	public ResponseEntity<Response> trashAndUntrash(@RequestParam String token, @RequestParam String noteId) {
		String message = noteService.trashAndUntrash(token, noteId);
		Response response = new Response(HttpStatus.OK.value(), message, null);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@GetMapping("/pinAndunpin")
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
}
