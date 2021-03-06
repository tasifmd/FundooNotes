package com.bridgelabz.fundoo.notes.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
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

import com.bridgelabz.fundoo.notes.dto.NotesDto;
import com.bridgelabz.fundoo.notes.model.Note;
import com.bridgelabz.fundoo.notes.service.INotesService;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.user.model.User;

/**
 * Purpose : Note controller class 
 * @author : Tasif Mohammed
 *
 */
@RestController
@CrossOrigin(allowedHeaders = "*" ,origins = "*")
@RequestMapping("/user/note")

//annotation for set environment file 
@PropertySource("classpath:message.properties")
public class NotesController {
	
	Logger logger = LoggerFactory.getLogger(NotesController.class);
	
	@Autowired
	private INotesService noteService;
	
	/**
	 * Purpose : Function for creating notes
	 * @param notesDto
	 * @param token
	 * @return
	 */
	@PostMapping("/create")
	public ResponseEntity<Response> creatingNote(HttpServletRequest request , @RequestBody NotesDto notesDto , @RequestHeader("token") String token){
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
	public ResponseEntity<Response> updatingNote(@RequestBody NotesDto notesDto , @RequestHeader String token , @RequestParam long noteId){
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
	public ResponseEntity<Response> deletingNote(@RequestHeader String token ,@RequestParam long noteId){
		Response responseStatus = noteService.delete(token, noteId);
		return new ResponseEntity<Response> (responseStatus,HttpStatus.OK);
	}

	/**
	 * Purpose : Function to get all the notes
	 * @param token
	 * @return
	 */
	@GetMapping("/getallnotes")
	public List<Note>  getAllNotes(@RequestHeader String token) {
		List<Note> listnotes = noteService.getAllNotes(token);
		return listnotes;
	}
	
	/**
	 * Purpose : Function to pin or unpin notes 
	 * @param token
	 * @param noteId
	 * @return
	 */
	@PutMapping("/pin")
	public ResponseEntity<Response> pinNote(@RequestHeader String token , @RequestParam long noteId){
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
	public ResponseEntity<Response> archiveNote(@RequestHeader String token , @RequestParam long noteId){
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
	public ResponseEntity<Response> trashNote(@RequestHeader String token, @RequestParam long noteId){
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
	public ResponseEntity<Response> deleteNote(@RequestHeader String token, @RequestParam long noteId){
		Response responseStatus = noteService.deletePermanently(token, noteId);
		return new ResponseEntity<Response> (responseStatus,HttpStatus.OK);
	}

	/**
	 * Purpose : Function to get all archive notes 
	 * @param token
	 * @return
	 */
	@GetMapping("/getarchivenotes")
	public List<Note>  getArchiveNotes(@RequestHeader String token) {
		List<Note> listnotes = noteService.getArchiveNotes(token);
		return listnotes;
	}
	
	/**
	 * Purpose : Function to get all trash notes 
	 * @param token
	 * @return
	 */
	@GetMapping("/gettrashnotes")
	public List<Note>  getTrashNotes(@RequestHeader String token) {
		List<Note> listnotes = noteService.getTrashNotes(token);
		return listnotes;
	}
	
	/**
	 * Purpose : Function to get all pinned notes 
	 * @param token
	 * @return
	 */
	@GetMapping("/getpinnednotes")
	public List<Note> getPinnedNotes(@RequestHeader String token){
		List<Note> listnotes = noteService.getPinnedNotes(token);
		return listnotes;
	}
	
	/**
	 * Purpose : Function to get all unpinned notes 
	 * @param token
	 * @return
	 */
	@GetMapping("/getunpinnednotes")
	public List<Note> getUnPinnedNotes(@RequestHeader String token){
		List<Note> listnotes = noteService.getUnPinnedNotes(token);
		return listnotes;
	}
	/**
	 * Purpose : Function to change color
	 * @param token
	 * @param noteId
	 * @param colorCode
	 * @return
	 */
	@PutMapping("/color")
	public ResponseEntity<Response> changeColor(@RequestHeader String token,@RequestParam long noteId,@RequestParam String colorCode) {
		Response responseStatus = noteService.setColor(token, colorCode, noteId);
		return new  ResponseEntity<Response> (responseStatus,HttpStatus.OK);
	}	
	/**
	 * Purpose : Function to add collaborator
	 * @param token
	 * @param email
	 * @param noteId
	 * @return
	 */
	@PutMapping("/addcollaborator")
	public  ResponseEntity<Response> addCollab(@RequestHeader String token,@RequestParam String email,@RequestParam long noteId) {
		Response responseStatus = noteService.addCollaborator(token, email, noteId);
		return new  ResponseEntity<Response> (responseStatus,HttpStatus.OK);
	}
	/**
	 * Purpose : Function to remove collaborator
	 * @param token
	 * @param email
	 * @param noteId
	 * @return
	 */
	@PutMapping("/removecollaborator")
	public ResponseEntity<Response> removeCollab(@RequestHeader String token,@RequestParam String email,@RequestParam long noteId) {
		Response responseStatus = noteService.removeCollaborator(token, email, noteId);
		return new  ResponseEntity<Response> (responseStatus,HttpStatus.OK);
	}
	/**
	 * Purpose : Function to get all collaborated notes
	 * @param token
	 * @return
	 */
	@GetMapping("/getallcollaboratednotes")
	public Set<Note> getCollaboratedNotes(@RequestHeader String token) {
		Set<Note> collaboratednotes = noteService.getCollaboratedNotes(token);
		return collaboratednotes;
	}
	/**
	 * Purpose : Function to get all collaborated users
	 * @param token
	 * @param noteId
	 * @return
	 */
	@GetMapping("/getallcollaborateduser")
	public Set<User> getCollaboratedUser(@RequestHeader String token , @RequestParam long noteId) {
		Set<User> collaboratedUser = noteService.getCollaboratedUser(token, noteId);
		return collaboratedUser;
	}
	@GetMapping("/search")
	public List<Note> searchNote(@RequestHeader String token , @RequestParam String query) {
		List<Note> notes = noteService.searchNote(query, token);
		return notes;
	}
	@PutMapping("/addreminder")
	public ResponseEntity<Response> addingReminder(@RequestHeader String token , @RequestParam long noteId , @RequestParam String reminder) {
		Response responseStatus = noteService.addReminder(token, noteId, reminder);
		return new  ResponseEntity<Response> (responseStatus,HttpStatus.OK);
	}
	@PutMapping("/removereminder")
	public ResponseEntity<Response> removingReminder(@RequestHeader String token , @RequestParam long noteId) { 
		Response responseStatus = noteService.removeReminder(token, noteId);
		return new  ResponseEntity<Response> (responseStatus,HttpStatus.OK);
	}
	
	@GetMapping("/getremainders")
	public ResponseEntity<String> getRemainder(@RequestHeader String token , @RequestParam long noteId) {
		String responseStatus = noteService.getRemainders(token, noteId);
		return new  ResponseEntity<String> (responseStatus,HttpStatus.OK);
	}
}
