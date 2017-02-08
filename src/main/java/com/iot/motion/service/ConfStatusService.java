package com.iot.motion.service;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iot.motion.dao.ConfRoomEventDao;
import com.iot.motion.domain.model.ConfRoomEvent;

@Service
public class ConfStatusService implements StatusService {

	private DateFormat df = null;
	
	@Autowired
	public ConfStatusService(){ df = new SimpleDateFormat("dd/MM/yy HH:mm:ss"); }
	
	@Autowired
	public ConfRoomEventDao confRoomEventDao;
	
	@Override
	public void insertUpdate(boolean status,long roomId) {
		//Status = 1 Conf Occupied
		if(status){
			insertConfInTime(roomId);
		}else{ // Status = 0 conf empty
			updateConfOutTime(roomId);
		}
	}
	@Override
	public void insertConfInTime(long roomId) {
		// Conf room is free, mark it as occupied 
		if(confRoomEventDao.find(roomId) == null){
			Calendar calobj = Calendar.getInstance();
			ConfRoomEvent confRoomEvent = new ConfRoomEvent(roomId,calobj.getTime(),null);
			confRoomEventDao.save(confRoomEvent);
			System.out.println("insertConfInTime :"+df.format(calobj.getTime()));
		}else {// Conf room Already Occupied
			System.out.println("Conf Room is already is occupied");
		}
		
	}

	@Override
	public void updateConfOutTime(long roomId) {
		Calendar calobj = Calendar.getInstance();
		confRoomEventDao.updateConfOutTime(roomId,calobj.getTime());
		System.out.println("updateConfOutTime :"+df.format(calobj.getTime()));
	}

}
