package com.bridgelabz.fundoo.user.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoo.notes.model.Note;
import com.bridgelabz.fundoo.user.model.User;

/**
 * Purpose : Creating IUserRepository interface to use features of JPA
 * @author : Tasif Mohammed
 *
 */
@Repository
public interface IUserRepository extends JpaRepository<User, Long>{

	public Optional<User> findByEmail(String email);
	
	public Set<Note> findAllCollaboratedNotesByUserId(long userId);
	
	public List<User> findAllUserByEmail(String email);
	
	
}
