package com.iot.motion.dao;

import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;

import com.iot.motion.domain.model.User;

//Imports ...

@Transactional
public interface UserDao extends CrudRepository<User, Long> {

	/**
	* This method will find an User instance in the database by its email.
	* Note that this method is not implemented and its working code will be
	* automagically generated from its signature by Spring Data JPA.
	*/
	public User findByEmail(String email);

}
