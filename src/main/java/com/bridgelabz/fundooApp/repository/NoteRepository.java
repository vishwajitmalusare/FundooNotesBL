package com.bridgelabz.fundooApp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bridgelabz.fundooApp.model.Note;

import java.lang.String;
import java.util.List;
import java.util.Optional;

public interface NoteRepository extends MongoRepository<Note, String> {

	Optional<Note> findByNoteId(String noteid);

	// Optional<Note> findByUserId(String userid);
	List<Note> findByUserId(String token);

	List<Note> findByUserIdAndIsArchive(String userId, boolean isArchive);

	Optional<Note> findByNoteIdAndUserId(String noteId, String userId);

}
