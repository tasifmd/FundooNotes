package com.bridgelabz.fundoo.notes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoo.notes.model.Label;
import com.bridgelabz.fundoo.notes.model.Note;

/**
 * Purpose : Repository class for Label
 * @author : Tasif Mohammed
 * 
 */
@Repository
public interface LabelRepository extends JpaRepository<Label, Long> {
	
	/**
	 * Purpose : Function to find label by labelId and userId
	 * @param labelId
	 * @param userId
	 * @return
	 */
	public Label findByLabelIdAndUserId(long labelId , long userId);
	
	/**
	 * Purpose : Function to get labels of user
	 * @param userId
	 * @return
	 */
	public List<Label> findByUserId(long userId);
	
	/**
	 * Purpose : Function to get label by userId and labelName
	 * @param userId
	 * @param labelName
	 * @return
	 */
	public Optional<Label> findByUserIdAndLabelName(long userId , String labelName);
	
	/**
	 * Purpose : Function to get all labels of note
	 * @param noteId
	 * @return
	 */
	@Query(value = "SELECT * from notes_list_label where note_note_id=:noteId" , nativeQuery=true)
	public List<Label> findAllLabelsByNotes(@Param("noteId")long notes);
	
	public List<Note> findAllNotesByLabelId(long labelId);
	
	public List<Label> findAllLabelByUserId(long userId);
	
	
}
