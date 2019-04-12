package com.bridgelabz.fundoo.label.controller;

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

import com.bridgelabz.fundoo.label.dto.LabelDto;
import com.bridgelabz.fundoo.label.service.ILabelService;
import com.bridgelabz.fundoo.response.Response;

@RestController
@RequestMapping("/user/note/label")
public class LabelController {
	
	@Autowired
	ILabelService labelService;
	
	@PostMapping("/create")
	ResponseEntity<Response> createLabel(@RequestBody LabelDto labelDto , @RequestParam String token) {
		Response statusResponse = labelService.createLabel(labelDto, token);
		return new ResponseEntity<Response>(statusResponse,HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/delete")
	ResponseEntity<Response> deleteLabel(@RequestParam String token , @RequestParam long labelId) {
		Response statusResponse = labelService.deleteLabel(labelId, token);
		return new ResponseEntity<Response>(statusResponse,HttpStatus.OK);
	}
	
	@PutMapping("/update")
	ResponseEntity<Response> updateLabel(@RequestParam String token , @RequestParam long labelId , @RequestBody LabelDto labelDto){
		Response statusResponse = labelService.updateLabel(labelId, token, labelDto);
		return new ResponseEntity<Response>(statusResponse,HttpStatus.OK);
	}
	
	@GetMapping("/getlabel")
	List<LabelDto> getLabel(@RequestParam String token){
		List<LabelDto> listLabel = labelService.getAllLabel(token);
		return listLabel;
	}
	
	@PutMapping("/addlebeltonote")
	ResponseEntity<Response> addNoteToLebel(@RequestParam long labelId , @RequestParam String token , @RequestParam long noteId){
		Response statusResponse = labelService.addNoteToLabel(labelId, token, noteId);
		return new ResponseEntity<Response>(statusResponse,HttpStatus.OK);
	}
	
	@GetMapping("/getlebelofnote")
	List<LabelDto> getLebelOfNote(@RequestParam String token, @RequestParam long noteId){
		List<LabelDto> listLabel = labelService.getLebelsOfNote(token, noteId);
		return listLabel;
	}
}
