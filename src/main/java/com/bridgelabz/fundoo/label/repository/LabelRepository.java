package com.bridgelabz.fundoo.label.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoo.label.model.Label;

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
	public List<Label> findAllLabelsByNoteId(long noteId);
}
