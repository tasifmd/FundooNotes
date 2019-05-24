package com.bridgelabz.fundoo.util;

import org.springframework.stereotype.Component;

import com.bridgelabz.fundoo.notes.model.Note;

@Component
public class NoteContainer {
	
	private Note note;
	private NoteOperation noteOperation;
	
	public NoteContainer() {
		super();
	}
	public Note getNote() {
		return note;
	}
	public void setNote(Note note) {
		this.note = note;
	}
	public NoteOperation getNoteOperation() {
		return noteOperation;
	}
	public void setNoteOperation(NoteOperation noteOperation) {
		this.noteOperation = noteOperation;
	}
	@Override
	public String toString() {
		return "NoteContainer [note=" + note + ", noteOperation=" + noteOperation + "]";
	}
	
}
