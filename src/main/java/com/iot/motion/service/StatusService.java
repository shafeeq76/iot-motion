package com.iot.motion.service;


public interface StatusService {
	//inser or update row based on status 
	public void insertUpdate(boolean status,long roomId);
	// insert new row in ConfRoomevent Table with Out time is null
	public void insertConfInTime(long roomId);
	// this method has to update outtime only the row where Out time is null
	public void updateConfOutTime(long roomId);
}