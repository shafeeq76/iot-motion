package com.iot.motion.controller;


import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iot.motion.dao.ConfRoomDao;
import com.iot.motion.dao.UserDao;
import com.iot.motion.domain.model.ConfRoom;
import com.iot.motion.domain.model.Greeting;
import com.iot.motion.domain.model.User;
import com.iot.motion.service.ConfStatusService;

@RestController
public class ConfStatusController {
	
	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private ConfRoomDao confRoomDao;
	@Autowired
	private ConfStatusService confStatusService;
	
	@RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }
	
	  /**
	   * GET /create  --> Create a new user and save it in the database.
	   */
	  @RequestMapping("/create")
	  public String create(String email, String name) {
	    String userId = "";
	    try {
	      User user = new User(email, name);
	      userDao.save(user);
	      userId = String.valueOf(user.getId());
	    }
	    catch (Exception ex) {
	      return "Error creating the user: " + ex.toString();
	    }
	    return "User succesfully created with id = " + userId;
	  }

	  /**
	   * GET /get-by-email  --> Return the id for the user having the passed
	   * email.
	   */
	  @RequestMapping("/get-by-email")
	  public String getByEmail(String email) {
	    String userId = "";
	    try {
	      User user = userDao.findByEmail(email);
	      userId = String.valueOf(user.getId());
	    }
	    catch (Exception ex) {
	      return "User not found"+ex.getMessage();
	    }
	    return "The user id is: " + userId;
	  }
	  
	  /**
	   * GET /update  --> Update the email and the name for the user in the 
	   * database having the passed id.
	   */
	  @RequestMapping("/updateuser")
	  public String updateUser(long id, String email, String name) {
	    try {
	      User user = userDao.findOne(id);
	      user.setEmail(email);
	      user.setName(name);
	      userDao.save(user);
	    }
	    catch (Exception ex) {
	      return "Error updating the user: " + ex.toString();
	    }
	    return "User succesfully updated!";
	  }
	  
	  // *********** Confrence Room Controllers ***************
	  /**
	   * GET /status?name=room1  --> Return the ConfRoom REST Object
	   */
	  @RequestMapping("/status/{name}")
	  public ConfRoom getConfRoomStatus(@PathVariable String name) {
		
		  // Room Status 
		  //http://localhost:8888/status/room1
		  //http://localhost:8888/status/room2
		  //http://localhost:8888/status/room3
		  
		ConfRoom confRoom = null;
	    try {
	      confRoom = confRoomDao.findByName(name);
	    }
	    catch (Exception ex) {
	      System.out.println("Wrong confrence room "+ex.getMessage());
	    }
	    return confRoom;
	  }
	  /**
	   * GET /status?name=room1  --> Return the ConfRoom REST Object
	   */
	  @RequestMapping("/update/{name}/{status}")
	  public String updateConfRoomStatus(@PathVariable String name,@PathVariable boolean status) {
		
		// REST API URLS for conf room status update
		//http://localhost:8888/update/room1/1 or 0
		//http://localhost:8888/update/room2/1 or 0 
		//http://localhost:8888/update/room3/1 or 0
		  
		ConfRoom confRoom = null;
	    try {
	      confRoomDao.updateConfRoom(status,name);
	      confRoom = confRoomDao.findByName(name);
	      confStatusService.insertUpdate(status,confRoom.getId());
	    }
	    catch (Exception ex) {
	      return "Wrong confrence room "+ex.toString();
	    }
	    // conf Occupied
	    if(confRoom.isStatus()){
	    	return "Confroom <b>"+confRoom.getName() +"</b> is Occupied";
	    }else{
	    	return "Confroom <b>"+confRoom.getName() +"</b> is Free now";
	    }
	  }
	  
}
