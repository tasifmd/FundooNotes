package com.bridgelabz.fundoo.elasticsearch;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.notes.model.Note;

@Service
public interface ElasticSearch {
	
	public Note create(Note note);
	public Note updateNote(Note note);
	public void deleteNote(Long NoteId);
	public List<Note> searchData(String query, long userId);
}
