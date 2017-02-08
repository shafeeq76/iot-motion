package com.iot.motion.dao;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.iot.motion.domain.model.ConfRoom;


@Transactional
public interface ConfRoomDao extends CrudRepository<ConfRoom, Long> {
	
	/**
	* This method will find an User instance in the database by its email.
	* Note that this method is not implemented and its working code will be
	* automagically generated from its signature by Spring Data JPA.
	*/
	public ConfRoom findByName(String name);
	
	@Modifying(clearAutomatically = true)
	@Query("update ConfRoom confRoom set confRoom.status =:status where confRoom.name =:name")
	void updateConfRoom(@Param("status") boolean status, @Param("name") String name);
}
