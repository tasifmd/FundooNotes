package com.bridgelabz.fundoo.notes.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
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

import com.bridgelabz.fundoo.notes.dto.NotesDto;
import com.bridgelabz.fundoo.notes.service.INotesService;
import com.bridgelabz.fundoo.response.Response;

/**
 * Purpose : Note controller class 
 * @author : Tasif Mohammed
 *
 */
@RestController

@RequestMapping("/user/notes")

//annotation for set environment file 
@PropertySource("classpath:message.properties")
public class NotesController {
	
	Logger logger = LoggerFactory.getLogger(NotesController.class);
	
	@Autowired
	INotesService noteService;
	
	/**
	 * Purpose : Function for creating notes
	 * @param notesDto
	 * @param token
	 * @return
	 */
	@PostMapping("/create")
	public ResponseEntity<Response> creatingNote(@RequestBody NotesDto notesDto , @RequestParam String token){
		logger.info(notesDto.toString());
		Response responseStatus = noteService.createNote(notesDto, token);
		return new ResponseEntity<Response>(responseStatus,HttpStatus.OK);
	}
	
	/**
	 * Purpose : Function for updating notes
	 * @param notesDto
	 * @param token
	 * @param noteId
	 * @return
	 */
	@PutMapping("/update")
	public ResponseEntity<Response> updatingNote(@RequestBody NotesDto notesDto , @RequestParam String token , @RequestParam long noteId){
		logger.info(notesDto.toString());
		Response responseStatus = noteService.updateNote(notesDto, token , noteId);
		return new ResponseEntity<Response> (responseStatus,HttpStatus.ACCEPTED);
	}
	
	
	/**
	 * Purpose : Function for deleting notes
	 * @param token
	 * @param noteId
	 * @return
	 */
	@PutMapping("/delete")
	public ResponseEntity<Response> deletingNote(@RequestParam String token ,@RequestParam long noteId){
		Response responseStatus = noteService.delete(token, noteId);
		return new ResponseEntity<Response> (responseStatus,HttpStatus.OK);
	}

	/**
	 * Purpose : Function to get all the notes
	 * @param token
	 * @return
	 */
	@GetMapping("/getallnotes")
	public List<NotesDto>  getAllNotes(@RequestParam String token) {
		List<NotesDto> listnotes = noteService.getAllNotes(token);
		return listnotes;
	}
	
	/**
	 * Purpose : Function to pin or unpin notes 
	 * @param token
	 * @param noteId
	 * @return
	 */
	@PutMapping("/pin")
	public ResponseEntity<Response> pinNote(@RequestParam String token , @RequestParam long noteId){
		Response responseStatus = noteService.pinAndUnPin(token, noteId);
		return new ResponseEntity<Response> (responseStatus,HttpStatus.OK);
	}
	
	/**
	 * Purpose : Function to archive or unarchive notes 
	 * @param token
	 * @param noteId
	 * @return
	 */
	@PutMapping("/archive")
	public ResponseEntity<Response> archiveNote(@RequestParam String token , @RequestParam long noteId){
		Response responseStatus = noteService.archiveAndUnArchive(token, noteId);
		return new ResponseEntity<Response> (responseStatus,HttpStatus.OK);
	}
	
	/**
	 * Purpose : Function to trash or untrash notes 
	 * @param token
	 * @param noteId
	 * @return
	 */
	@PutMapping("/trash")
	public ResponseEntity<Response> trashNote(@RequestParam String token, @RequestParam long noteId){
		Response responseStatus = noteService.trashAndUnTrash(token, noteId);
		return new ResponseEntity<Response> (responseStatus,HttpStatus.OK);
	}
	/**
	 * Purpose : Function to delete notes permanently
	 * @param token
	 * @param noteId
	 * @return
	 */
	@DeleteMapping("/delete")
	public ResponseEntity<Response> deleteNote(@RequestParam String token, @RequestParam long noteId){
		Response responseStatus = noteService.deletePermanently(token, noteId);
		return new ResponseEntity<Response> (responseStatus,HttpStatus.OK);
	}

	/**
	 * Purpose : Function to get all archive notes 
	 * @param token
	 * @return
	 */
	@GetMapping("/getarchivenotes")
	public List<NotesDto>  getArchiveNotes(@RequestParam String token) {
		List<NotesDto> listnotes = noteService.getArchiveNotes(token);
		return listnotes;
	}
	
	/**
	 * Purpose : Function to get all trash notes 
	 * @param token
	 * @return
	 */
	@GetMapping("/gettrashnotes")
	public List<NotesDto>  getTrashNotes(@RequestParam String token) {
		List<NotesDto> listnotes = noteService.getTrashNotes(token);
		return listnotes;
	}
}
