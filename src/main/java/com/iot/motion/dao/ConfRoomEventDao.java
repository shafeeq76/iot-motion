package com.iot.motion.dao;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.iot.motion.domain.model.ConfRoomEvent;

@Transactional
public interface ConfRoomEventDao extends CrudRepository<ConfRoomEvent, Long> {
	
	/**
	* This method will find an User instance in the database by its email.
	* Note that this method is not implemented and its working code will be
	* automagically generated from its signature by Spring Data JPA.
	*/
	public List<ConfRoomEvent> findByConfRoomId(long id);
	
	@Modifying(clearAutomatically = true)
	@Query("update ConfRoomEvent confRoomEvent set confRoomEvent.outTime =:outTime "
			+ "where confRoomEvent.confRoomId =:roomId and confRoomEvent.outTime IS NULL")
	void updateConfOutTime(@Param("roomId") long roomId,@Param("outTime") Date outTime);
	
	@Query("SELECT c from ConfRoomEvent c where c.confRoomId =:confRoomId and c.outTime IS NULL")
	public ConfRoomEvent find(@Param("confRoomId") long confRoomId);
	
}
