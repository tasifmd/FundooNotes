package com.bridgelabz.fundoo.label.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.label.dto.LabelDto;
import com.bridgelabz.fundoo.response.Response;

@Service
public interface ILabelService {
	public Response createLabel(LabelDto labelDto , String token);
	public Response deleteLabel(long labelId ,String token);
	public Response updateLabel(long labelId , String token , LabelDto labelDto);
	public List<LabelDto> getAllLabel(String token);
	public Response addNoteToLabel(long labelId ,String token , long noteId);
	public List<LabelDto> getLebelsOfNote(String token , long noteId);
}
