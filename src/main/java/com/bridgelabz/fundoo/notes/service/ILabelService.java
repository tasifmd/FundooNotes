package com.bridgelabz.fundoo.notes.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.notes.dto.LabelDto;
import com.bridgelabz.fundoo.notes.dto.NotesDto;
import com.bridgelabz.fundoo.response.Response;

/**
 * Purpose : Service class for label
 * @author : Tasif Mohammed
 *
 */
@Service
public interface ILabelService {
	
	
	/**
	 * Purpose : Function to create label
	 * @param labelDto
	 * @param token
	 * @return
	 */
	public Response createLabel(LabelDto labelDto , String token);
	
	
	/**
	 * Purpose : Function to delete label
	 * @param labelId
	 * @param token
	 * @return
	 */
	public Response deleteLabel(long labelId ,String token);
	
	
	/**
	 * Purpose : Function to update label
	 * @param labelId
	 * @param token
	 * @param labelDto
	 * @return
	 */
	public Response updateLabel(long labelId , String token , LabelDto labelDto);
	
	
	/**
	 * Purpose : Function to get all labels
	 * @param token
	 * @return
	 */
	public List<LabelDto> getAllLabel(String token);
	
	
	/**
	 * Purpose : Function to add note to label
	 * @param labelId
	 * @param token
	 * @param noteId
	 * @return
	 */
	public Response addLabelToNote(long labelId ,String token , long noteId);
	
	
	/**
	 * Purpose : Function to get all labels of note
	 * @param token
	 * @param noteId
	 * @return
	 */
	public List<LabelDto> getLebelsOfNote(String token , long noteId);
	
	
	/**
	 * Purpose : Function to remove label from note
	 * @param labelId
	 * @param token
	 * @param noteId
	 * @return
	 */
	public Response removeLabelFromNote(long labelId ,String token , long noteId);
	
	/**
	 * Purpose : Function to get notes of label
	 * @param token
	 * @param labelId
	 * @return
	 */
	public List<NotesDto> getNotesOfLabel(String token , long labelId );
}
