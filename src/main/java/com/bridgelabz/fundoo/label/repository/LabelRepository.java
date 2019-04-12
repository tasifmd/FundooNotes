package com.bridgelabz.fundoo.label.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoo.label.model.Label;

@Repository
public interface LabelRepository extends JpaRepository<Label, Long> {
	
	public Label findByLabelIdAndUserId(long labelId , long userId);
//	public Optional<Label> findByUserIdNoteIdAndLabelId(long labelId , long noteId , long userId);
	public List<Label> findByUserId(long userId);
	public Optional<Label> findByUserIdAndLabelName(long userId , String labelName);
	public List<Label> findAllLabelsByNoteId(long noteId);
}
