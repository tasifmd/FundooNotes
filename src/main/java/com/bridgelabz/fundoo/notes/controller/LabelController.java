package com.bridgelabz.fundoo.notes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.notes.dto.LabelDto;
import com.bridgelabz.fundoo.notes.dto.NotesDto;
import com.bridgelabz.fundoo.notes.service.ILabelService;
import com.bridgelabz.fundoo.response.Response;

/**
 * Controller class for label
 * @author : Tasif Mohammed
 *
 */
@RestController
@RequestMapping("/user/note/label")
public class LabelController {
	
	@Autowired
	private ILabelService labelService;
	
	/**
	 * Purpose : Function to create label
	 * @param labelDto
	 * @param token
	 * @return
	 */
	@PostMapping("/create")
	ResponseEntity<Response> createLabel(@RequestBody LabelDto labelDto , @RequestHeader String token) {
		Response statusResponse = labelService.createLabel(labelDto, token);
		return new ResponseEntity<Response>(statusResponse,HttpStatus.ACCEPTED);
	}
	
	/**
	 * Purpose : Function to delete label 
	 * @param token
	 * @param labelId
	 * @return
	 */
	@DeleteMapping("/delete")
	ResponseEntity<Response> deleteLabel(@RequestHeader String token , @RequestParam long labelId) {
		Response statusResponse = labelService.deleteLabel(labelId, token);
		return new ResponseEntity<Response>(statusResponse,HttpStatus.OK);
	}
	
	/**
	 * Purpose : Function to update label
	 * @param token
	 * @param labelId
	 * @param labelDto
	 * @return
	 */
	@PutMapping("/update")
	ResponseEntity<Response> updateLabel(@RequestHeader String token , @RequestParam long labelId , @RequestBody LabelDto labelDto){
		Response statusResponse = labelService.updateLabel(labelId, token, labelDto);
		return new ResponseEntity<Response>(statusResponse,HttpStatus.OK);
	}
	
	/**
	 * Purpose : Function to get all labels
	 * @param token
	 * @return
	 */
	@GetMapping("/getlabel")
	List<LabelDto> getLabel(@RequestHeader String token){
		List<LabelDto> listLabel = labelService.getAllLabel(token);
		return listLabel;
	}
	
	/**
	 * Purpose : Function to add note to label
	 * @param labelId
	 * @param token
	 * @param noteId
	 * @return
	 */
	@PutMapping("/addlebeltonote")
	ResponseEntity<Response> addNoteToLebel(@RequestParam long labelId , @RequestHeader String token , @RequestParam long noteId){
		Response statusResponse = labelService.addLabelToNote(labelId, token, noteId);
		return new ResponseEntity<Response>(statusResponse,HttpStatus.OK);
	}
	
	/**
	 * Purpose : Function to get all labels of note
	 * @param token
	 * @param noteId
	 * @return
	 */
	@GetMapping("/getlebelofnote")
	List<LabelDto> getLebelOfNote(@RequestHeader String token, @RequestParam long noteId){
		List<LabelDto> listLabel = labelService.getLebelsOfNote(token, noteId);
		return listLabel;
	}
	
	/**
	 * Purpose : Function to remove label from note
	 * @param token
	 * @param noteId
	 * @param labelId
	 * @return
	 */
	@PutMapping("/removefromnote")
	ResponseEntity<Response> removeFromNote(@RequestHeader String token, @RequestParam long noteId , @RequestParam long labelId){
		Response statusResponse = labelService.removeLabelFromNote(labelId, token, noteId);
		return new ResponseEntity<Response>(statusResponse,HttpStatus.OK);
	}
	
	/**
	 * Purpose : Function to get notes of label
	 * @param token
	 * @param labelId
	 * @return
	 */
	@GetMapping("/getnotesoflabel")
	List<NotesDto> getNotesOfLabel(@RequestHeader String token , @RequestParam long labelId){
		List<NotesDto> listNotes = labelService.getNotesOfLabel(token, labelId);
		return listNotes;
	}
}
