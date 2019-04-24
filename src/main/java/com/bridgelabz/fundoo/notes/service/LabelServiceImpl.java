package com.bridgelabz.fundoo.notes.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.exception.LabelException;
import com.bridgelabz.fundoo.exception.NotesException;
import com.bridgelabz.fundoo.exception.TokenException;
import com.bridgelabz.fundoo.notes.dto.LabelDto;
import com.bridgelabz.fundoo.notes.dto.NotesDto;
import com.bridgelabz.fundoo.notes.model.Label;
import com.bridgelabz.fundoo.notes.model.Note;
import com.bridgelabz.fundoo.notes.repository.INotesRepository;
import com.bridgelabz.fundoo.notes.repository.LabelRepository;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.user.model.User;
import com.bridgelabz.fundoo.user.repository.IUserRepository;
import com.bridgelabz.fundoo.util.StatusHelper;
import com.bridgelabz.fundoo.util.UserToken;


@Service("labelService")
@PropertySource("classpath:message.properties")
public class LabelServiceImpl implements ILabelService{

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private LabelRepository labelRepository;
	
	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private INotesRepository notesRepository;
	
	@Autowired
	private UserToken userToken;
	
	@Autowired
	private Environment environment;
	
	/* (non-Javadoc)
	 * @see com.bridgelabz.fundoo.label.service.ILabelService#createLabel(com.bridgelabz.fundoo.label.dto.LabelDto, java.lang.String)
	 */
	@Override
	public Response createLabel(LabelDto labelDto, String token) {
		long userId = userToken.tokenVerify(token);
		Optional<User> user = userRepository.findById(userId);
		if(!user.isPresent()) {
			throw new LabelException("Invalid input", -6);
		}
		if(labelDto.getLabelName().isEmpty()) {
			throw new LabelException("Label has no name", -6);
		}
		Optional<Label> labelAvailability = labelRepository.findByUserIdAndLabelName(userId, labelDto.getLabelName());
		if(labelAvailability.isPresent()) {
			throw new LabelException("Label already exist", -6);
		}
		
		Label label = modelMapper.map(labelDto,Label.class);
		
		label.setLabelName(labelDto.getLabelName());
		label.setUserId(userId);
		label.setCreatedDate(LocalDateTime.now());
		label.setModifiedDate(LocalDateTime.now());
		labelRepository.save(label);
		user.get().getLabel().add(label);
		userRepository.save(user.get());
		Response response = StatusHelper.statusInfo(environment.getProperty("status.label.created"), Integer.parseInt(environment.getProperty("status.success.code")));
		return response;
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.fundoo.label.service.ILabelService#deleteLabel(long, java.lang.String)
	 */
	@Override
	public Response deleteLabel(long labelId, String token) {
		long userId = userToken.tokenVerify(token);
		Optional<User> user = userRepository.findById(userId);
		if(!user.isPresent()) {
			throw new LabelException("Invalid input", -6);
		}
		Label label = labelRepository.findByLabelIdAndUserId(labelId, userId);
		if(label == null) {
			throw new LabelException("Invalid input", -6);
		}
		labelRepository.delete(label);
		Response response = StatusHelper.statusInfo(environment.getProperty("status.label.deleted"), Integer.parseInt(environment.getProperty("status.success.code")));
		return response;

	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.fundoo.label.service.ILabelService#updateLabel(long, java.lang.String, com.bridgelabz.fundoo.label.dto.LabelDto)
	 */
	@Override
	public Response updateLabel(long labelId , String token ,LabelDto labelDto) {
		long userId = userToken.tokenVerify(token);
		Optional<User> user = userRepository.findById(userId);
		if(!user.isPresent()) {
			throw new LabelException("Invalid input", -6);
		}
		Label label = labelRepository.findByLabelIdAndUserId(labelId, userId);
		if(label == null ) {
			throw new LabelException("No label exist", -6);
		}
		if(labelDto.getLabelName().isEmpty()) {
			throw new LabelException("Label has no name", -6);
		}
		Optional<Label> labelAvailability = labelRepository.findByUserIdAndLabelName(userId, labelDto.getLabelName());
		if(labelAvailability.isPresent()) {
			throw new LabelException("Label already exist", -6);
		}
		label.setLabelName(labelDto.getLabelName());
		label.setModifiedDate(LocalDateTime.now());
		labelRepository.save(label);
		Response response = StatusHelper.statusInfo(environment.getProperty("status.label.updated"), Integer.parseInt(environment.getProperty("status.success.code")));
		return response;
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.fundoo.label.service.ILabelService#getAllLabel(java.lang.String)
	 */
	@Override
	public List<LabelDto> getAllLabel(String token) {
		long userId = userToken.tokenVerify(token);
		Optional<User> user = userRepository.findById(userId);
		if(!user.isPresent()) {
			throw new LabelException("Invalid input", -6);
		}
		
		List<Label> labels = labelRepository.findByUserId(userId);
		List<LabelDto> listLabel = new ArrayList<>();
		for(Label noteLabel : labels) {
			LabelDto labelDto = modelMapper.map(noteLabel, LabelDto.class);
			listLabel.add(labelDto);
		}
		return listLabel;
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.fundoo.label.service.ILabelService#addNoteToLabel(long, java.lang.String, long)
	 */
	@Override
	public Response addLabelToNote(long labelId, String token , long noteId) {
		long userId = userToken.tokenVerify(token);
		Optional<User> user = userRepository.findById(userId);
		if(!user.isPresent()) {
			throw new LabelException("Invalid input", -6);
		}
		Label label = labelRepository.findByLabelIdAndUserId(labelId, userId);
		if(label == null) {
			throw new LabelException("No such lebel exist", -6);
		}
		Note note =  notesRepository.findByIdAndUserId(noteId, userId);
		if(note == null) {
			throw new LabelException("No such note exist", -6);
		}
		label.setModifiedDate(LocalDateTime.now());
		label.getNotes().add(note);
		note.getListLabel().add(label);
		note.setModified(LocalDateTime.now());
		labelRepository.save(label);
		notesRepository.save(note);
		Response response = StatusHelper.statusInfo(environment.getProperty("status.label.addedtonote"), Integer.parseInt(environment.getProperty("status.success.code")));
		return response;
	}

	

	/* (non-Javadoc)
	 * @see com.bridgelabz.fundoo.label.service.ILabelService#removeLabelFromNote(long, java.lang.String, long)
	 */
	@Override
	public Response removeLabelFromNote(long labelId ,String token , long noteId) {
		long userId = userToken.tokenVerify(token);
		Optional<User> user = userRepository.findById(userId);
		if(!user.isPresent()) {
			throw new LabelException("Invalid input", -6);
		}
		Label label = labelRepository.findByLabelIdAndUserId(labelId , userId);
		if(label == null) {
			throw new LabelException("No such lebel exist", -6);
		}
		Note note =  notesRepository.findByIdAndUserId(noteId, userId);
		if(note == null) {
			throw new LabelException("No such note exist", -6);
		}
		label.setModifiedDate(LocalDateTime.now());
		note.getListLabel().remove(label);
		note.setModified(LocalDateTime.now());
		labelRepository.save(label);
		notesRepository.save(note);
		Response response = StatusHelper.statusInfo(environment.getProperty("status.label.removedfromnote"), Integer.parseInt(environment.getProperty("status.success.code")));
		return response;
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.fundoo.label.service.ILabelService#getLebelsOfNote(java.lang.String, long)
	 */
	@Override
	public List<LabelDto> getLebelsOfNote(String token, long noteId) {
		long userId = userToken.tokenVerify(token);
		Optional<User> user = userRepository.findById(userId);
		if(!user.isPresent()) {
			throw new LabelException("User does not exist", -6);
		}
		Optional<Note> note = notesRepository.findById(noteId);
		if(!note.isPresent()) {
			throw new NotesException("Note does not exist", -6);
		}
		List<Label> lebel = note.get().getListLabel();
		
		List<LabelDto> listLabel = new ArrayList<>();
		for(Label noteLabel : lebel) {
			LabelDto labelDto = modelMapper.map(noteLabel, LabelDto.class);
			listLabel.add(labelDto);
		}
		return listLabel;
		
	}
	
	@Override
	public List<NotesDto> getNotesOfLabel(String token, long labelId) {
		long userId = userToken.tokenVerify(token);
		Optional<User> user = userRepository.findById(userId);
		if(!user.isPresent()) {
			throw new TokenException("Invalid input", -6);
		}
		Optional<Label> label = labelRepository.findById(labelId);
		if(!label.isPresent()) {
			throw new LabelException("No lebel exist", -6);
		}
		List<Note> notes = label.get().getNotes();
		List<NotesDto> listNotes = new ArrayList<>();
		for (Note usernotes : notes) {
			NotesDto noteDto = modelMapper.map(usernotes, NotesDto.class);
			listNotes.add(noteDto);
		}
		return listNotes;
	}
	
	
}
