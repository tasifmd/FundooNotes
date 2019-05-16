package com.bridgelabz.fundoo.notes.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.exception.EmailException;
import com.bridgelabz.fundoo.exception.NotesException;
import com.bridgelabz.fundoo.notes.dto.NotesDto;
import com.bridgelabz.fundoo.notes.model.Note;
import com.bridgelabz.fundoo.notes.repository.INotesRepository;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.user.model.User;
import com.bridgelabz.fundoo.user.repository.IUserRepository;
import com.bridgelabz.fundoo.util.StatusHelper;
import com.bridgelabz.fundoo.util.UserToken;

/**
 * Purpose : Implementation class of INoteService
 * @author : Tasif Mohammed
 *
 */
@Service("notesService")
@PropertySource("classpath:message.properties")
public class NotesServiceImpl implements INotesService {

	Logger logger = LoggerFactory.getLogger(NotesServiceImpl.class);
	
	@Autowired
	private UserToken userToken;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private INotesRepository notesRepository;
	
	@Autowired
	private Environment environment;
	
	
	/* (non-Javadoc)
	 * @see com.bridgelabz.fundoo.notes.service.INotesService#createNote(com.bridgelabz.fundoo.notes.dto.NotesDto, java.lang.String)
	 */
	@Override
	public Response createNote(NotesDto notesDto, String token) {
		
		long id = userToken.tokenVerify(token);
		logger.info(notesDto.toString());
		if(notesDto.getTitle().isEmpty() && notesDto.getDescription().isEmpty()) {
			
			throw new NotesException("Title and description are empty", -5);

		}
		Note notes = modelMapper.map(notesDto, Note.class);
		Optional<User> user = userRepository.findById(id);
		notes.setUserId(id);
		notes.setCreated(LocalDateTime.now());
		notes.setModified(LocalDateTime.now());
		user.get().getNotes().add(notes);
		notesRepository.save(notes);
		userRepository.save(user.get());
		Response response = StatusHelper.statusInfo(environment.getProperty("status.notes.createdSuccessfull"), Integer.parseInt(environment.getProperty("status.success.code")));
		return response;
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.fundoo.notes.service.INotesService#updateNote(com.bridgelabz.fundoo.notes.dto.NotesDto, java.lang.String, long)
	 */
	@Override
	public Response updateNote(NotesDto notesDto , String token , long noteId) {
		if(notesDto.getTitle().isEmpty() && notesDto.getDescription().isEmpty()) {
			throw new NotesException("Title and description are empty", -5);
		}
		
		long id = userToken.tokenVerify(token);
		Note notes = notesRepository.findByIdAndUserId(noteId, id);
		notes.setTitle(notesDto.getTitle());
		notes.setDescription(notesDto.getDescription());
		notes.setColorCode(notesDto.getColor());
		notes.setModified(LocalDateTime.now());
		notesRepository.save(notes);
		Response response = StatusHelper.statusInfo(environment.getProperty("status.notes.updated"),Integer.parseInt(environment.getProperty("status.success.code")));
		return response;
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.fundoo.notes.service.INotesService#delete(java.lang.String, long)
	 */
	@Override
	public Response delete(String token, long noteId) {
		long id = userToken.tokenVerify(token);
		Note notes = notesRepository.findByIdAndUserId(noteId, id);
		if(notes == null) {
			throw new NotesException("Invalid input", -5);
		}
		if(notes.isTrash() == false) {
			notes.setTrash(true);
			notes.setModified(LocalDateTime.now());
			notesRepository.save(notes);
			Response response = StatusHelper.statusInfo(environment.getProperty("status.note.trashed"),Integer.parseInt(environment.getProperty("status.success.code")));
			return response;
		}
		Response response = StatusHelper.statusInfo(environment.getProperty("status.note.trashError"),Integer.parseInt(environment.getProperty("status.note.errorCode")));
		return response;
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.fundoo.notes.service.INotesService#getAllNotes(java.lang.String)
	 */
	@Override
	public List<Note> getAllNotes(String token) {
		long id = userToken.tokenVerify(token);
		List<Note> notes = (List<Note>) notesRepository.findByUserId(id);
		List<Note> listNotes = new ArrayList<>();
		for(Note userNotes : notes) {
			//NotesDto notesDto = modelMapper.map(userNotes, NotesDto.class);
			if(userNotes.isArchive() == false && userNotes.isTrash() == false) {
				listNotes.add(userNotes);
				
			}
		}
		return listNotes;
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.fundoo.notes.service.INotesService#pinAndUnPin(java.lang.String, long)
	 */
	@Override
	public Response pinAndUnPin(String token, long noteId) {
		long id = userToken.tokenVerify(token);
		Note notes = notesRepository.findByIdAndUserId(noteId, id);
		if(notes == null) {
			throw new NotesException("Invalid input", -5);
		}
		if(notes.isPin() == false) {
			notes.setPin(true);
			notesRepository.save(notes);
			Response response = StatusHelper.statusInfo(environment.getProperty("status.note.pinned"),Integer.parseInt(environment.getProperty("status.success.code")));
			return response;
		}
		else {
			notes.setPin(false);
			notesRepository.save(notes);
			Response response = StatusHelper.statusInfo(environment.getProperty("status.note.unpinned"),Integer.parseInt(environment.getProperty("status.success.code")));
			return response;
		}
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.fundoo.notes.service.INotesService#archiveAndUnArchive(java.lang.String, long)
	 */
	@Override
	public Response archiveAndUnArchive(String token, long noteId) {
		long id = userToken.tokenVerify(token);
		Note notes = notesRepository.findByIdAndUserId(noteId, id);
		if(notes == null) {
			throw new NotesException("Invalid input", -5);
		}
		if(notes.isArchive() == false) {
			notes.setArchive(true);
			notesRepository.save(notes);
			Response response = StatusHelper.statusInfo(environment.getProperty("status.note.archieved"),Integer.parseInt(environment.getProperty("status.success.code")));
			return response;
		}
		else {
			notes.setArchive(false);
			notesRepository.save(notes);
			Response response = StatusHelper.statusInfo(environment.getProperty("status.note.unarchieved"),Integer.parseInt(environment.getProperty("status.success.code")));
			return response;
		}
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.fundoo.notes.service.INotesService#trashAndUnTrash(java.lang.String, long)
	 */
	@Override
	public Response trashAndUnTrash(String token, long noteId) {
		long id = userToken.tokenVerify(token);
		Note notes = notesRepository.findByIdAndUserId(noteId, id);
		if(notes.isTrash() == false) {
			notes.setTrash(true);
			notesRepository.save(notes);
			Response response = StatusHelper.statusInfo(environment.getProperty("status.note.trashed"),Integer.parseInt(environment.getProperty("status.success.code")));
			return response;
		}
		else {
			notes.setTrash(false);
			notesRepository.save(notes);
			Response response = StatusHelper.statusInfo(environment.getProperty("status.note.untrashed"),Integer.parseInt(environment.getProperty("status.success.code")));
			return response;
		}
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.fundoo.notes.service.INotesService#deletePermanently(java.lang.String, long)
	 */
	@Override
	public Response deletePermanently(String token, long noteId) {
		long id = userToken.tokenVerify(token);
		Note notes = notesRepository.findByIdAndUserId(noteId, id);
		if(notes.isTrash() == true) {
			notesRepository.delete(notes);
			Response response = StatusHelper.statusInfo(environment.getProperty("status.note.deleted"),Integer.parseInt(environment.getProperty("status.success.code")));
			return response;
		}else {
			Response response = StatusHelper.statusInfo(environment.getProperty("status.note.notdeleted"),Integer.parseInt(environment.getProperty("status.note.errorCode")));
			return response;
		}
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.fundoo.notes.service.INotesService#getArchiveNotes(java.lang.String)
	 */
	@Override
	public List<Note> getArchiveNotes(String token) {
		long id = userToken.tokenVerify(token);
		List<Note> notes = (List<Note>) notesRepository.findByUserId(id);
		List<Note> listNotes = new ArrayList<>();
		for(Note userNotes : notes) {
			//NotesDto notesDto = modelMapper.map(userNotes, NotesDto.class);
			if(userNotes.isArchive() == true) {
				listNotes.add(userNotes);
			}
		}
		return listNotes;
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.fundoo.notes.service.INotesService#getTrashNotes(java.lang.String)
	 */
	@Override
	public List<Note> getTrashNotes(String token) {
		long id = userToken.tokenVerify(token);
		List<Note> notes = (List<Note>) notesRepository.findByUserId(id);
		List<Note> listNotes = new ArrayList<>();
		for(Note userNotes : notes) {
			if(userNotes.isTrash() == true) {
				listNotes.add(userNotes);
			}
		}
		return listNotes;
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.fundoo.notes.service.INotesService#getPinnedNotes(java.lang.String)
	 */
	@Override
	public List<Note> getPinnedNotes(String token) {
		long id = userToken.tokenVerify(token);
		List<Note> notes = (List<Note>) notesRepository.findByUserId(id);
		List<Note> listNotes = new ArrayList<>();
		for(Note userNotes : notes) {
			if(userNotes.isPin() == true) {
				listNotes.add(userNotes);
			}
		}
		return listNotes;
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.fundoo.notes.service.INotesService#getUnPinnedNotes(java.lang.String)
	 */
	@Override
	public List<Note> getUnPinnedNotes(String token) {
		long id = userToken.tokenVerify(token);
		List<Note> notes = (List<Note>) notesRepository.findByUserId(id);
		List<Note> listNotes = new ArrayList<>();
		for(Note userNotes : notes) {
			if(userNotes.isPin() == false) {
				listNotes.add(userNotes);
			}
		}
		return listNotes;
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.fundoo.notes.service.INotesService#setColor(java.lang.String, java.lang.String, long)
	 */
	@Override
	public Response setColor(String token, String colorCode , long noteId) {
		long uderId = userToken.tokenVerify(token);
		Note note = notesRepository.findByIdAndUserId(noteId, uderId);
		note.setColorCode(colorCode);
		note.setModified(LocalDateTime.now());
		notesRepository.save(note);
		Response response = StatusHelper.statusInfo(environment.getProperty("status.note.color"),Integer.parseInt(environment.getProperty("status.success.code")));
		return response;
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.fundoo.notes.service.INotesService#addCollaborator(java.lang.String, java.lang.String, long)
	 */
	@Override
	public Response addCollaborator(String token, String email, long noteId) {
		long userId = userToken.tokenVerify(token);
		Optional<User> user = userRepository.findByEmail(email);
		if(!user.isPresent()) {
			throw new EmailException("No user exist", -4);
		}
		Note note = notesRepository.findByIdAndUserId(noteId, userId);
		if(note == null)
			throw new NotesException("No note exist", -5);
		//user.get().getNotes().add(note);
		user.get().getCollaboratedNotes().add(note);
		note.getCollaboratedUser().add(user.get());
		userRepository.save(user.get());
		notesRepository.save(note);
		Response response = StatusHelper.statusInfo(environment.getProperty("status.collab.success"),Integer.parseInt(environment.getProperty("status.success.code")));
		return response;
	}

	@Override
	public Response removeCollaborator(String token, String email, long noteId) {
		long userId = userToken.tokenVerify(token);
		Optional<User> user = userRepository.findByEmail(email);
		if(!user.isPresent()) {
			throw new EmailException("No user exist", -4);
		}
		Note note = notesRepository.findByIdAndUserId(noteId, userId);
		if(note == null)
			throw new NotesException("No note exist", -5);
		user.get().getCollaboratedNotes().remove(note);
		note.getCollaboratedUser().remove(user.get());
		userRepository.save(user.get());
		notesRepository.save(note);
		Response response = StatusHelper.statusInfo(environment.getProperty("status.collab.remove"),Integer.parseInt(environment.getProperty("status.success.code")));
		return response;
	}

	
}
