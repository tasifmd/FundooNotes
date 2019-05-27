package com.bridgelabz.fundoo.notes.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.notes.dto.NotesDto;
import com.bridgelabz.fundoo.notes.model.Note;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.user.model.User;

/**
 * Purpose : Service class for notes
 * @author admin1
 *
 */
@Service
public interface INotesService {

	/**
	 * Purpose : Function for creating notes
	 * @param notesDto
	 * @param token
	 * @return
	 */
	public Response createNote(NotesDto notesDto ,String token);
	/**
	 * Purpose : Function for updating notes
	 * @param notesDto
	 * @param token
	 * @param noteId
	 * @return
	 */
	public Response updateNote(NotesDto notesDto,String token,long noteId);
	/**
	 * Purpose : Function for deleting notes
	 * @param token
	 * @param noteId
	 * @return
	 */
	public Response delete(String token , long noteId);
	/**
	 * Purpose : Function to get all the notes
	 * @param token
	 * @return
	 */
	public List<Note>  getAllNotes(String token);
	/**
	 * Purpose : Function to pin or unpin notes 
	 * @param token
	 * @param noteId
	 * @return
	 */
	public Response pinAndUnPin(String token , long noteId);
	/**
	 * Purpose : Function to archive or unarchive notes 
	 * @param token
	 * @param noteId
	 * @return
	 */
	public Response archiveAndUnArchive(String token , long noteId);
	/**
	 * Purpose : Function to trash or untrash notes 
	 * @param token
	 * @param noteId
	 * @return
	 */
	public Response trashAndUnTrash(String token , long noteId);
	/**
	 * Purpose : Function to delete notes permanently
	 * @param token
	 * @param noteId
	 * @return
	 */
	public Response deletePermanently(String token , long noteId);
	/**
	 * Purpose : Function to get all archive notes
	 * @param token
	 * @return
	 */
	
	public List<Note> getArchiveNotes(String token);
	/**
	 * Purpose : Function to get all trash notes 
	 * @param token
	 * @return
	 */
	public List<Note> getTrashNotes(String token);
	
	/**
	 * Purpose : Function to get all pinned notes 
	 * @param token
	 * @return
	 */
	public List<Note> getPinnedNotes(String token);
	
	/**
	 * Purpose : Function to get all unpinned notes 
	 * @param token
	 * @return
	 */
	public List<Note> getUnPinnedNotes(String token);
	
	/**
	 * Purpose : Function to set color
	 * @param token
	 * @param colorCode
	 * @param noteId
	 * @return
	 */
	public Response setColor(String token , String colorCode , long noteId);
	
	/**
	 * Purpose : Function to add collaborator
	 * @param token
	 * @param email
	 * @param noteId
	 * @return
	 */
	public Response addCollaborator(String token,String email,long noteId);
	
	/**
	 * Purpose : Function to remove collaborator
	 * @param token
	 * @param email
	 * @param noteId
	 * @return
	 */
	public Response removeCollaborator(String token,String email,long noteId);
	
	/**
	 * Purpose : Function to get collaborated notes
	 * @param token
	 * @return
	 */
	public Set<Note> getCollaboratedNotes(String token);
	
	/**
	 * Purpose : Function to get collaborated user
	 * @param token
	 * @param noteId
	 * @return
	 */
	public Set<User> getCollaboratedUser(String token ,long noteId);
	
	public List<Note> searchNote(String query, String token);
	
	public Response addReminder(String token, long noteId , String reminder);
	
	public Response removeReminder(String token ,long noteId);
	
	public String getRemainders(String token,long noteId);
}
