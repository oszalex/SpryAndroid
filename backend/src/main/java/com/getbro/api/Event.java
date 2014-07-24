package com.getbro.api;

import javax.xml.bind.annotation.*;
import java.util.*;  


@XmlRootElement
public class Event {
	private String desc;
	private int eventID;
	private long createdAt;

	public static int countID=0;
        public List<User> invited = new LinkedList<User>();
	private int usercount=0;
        
	private Event() {}

	@XmlElement(name="raw")
	public String getRaw(){
		return desc;
	}

	@XmlElement(name="time")
	public long getTime(){
		return System.currentTimeMillis();
	}

	@XmlElement(name="createdAt")
	public long getCreatedAt(){
		return  createdAt;
	}

	@XmlElement(name="id")
	public int getId(){
		return eventID;
	}
   
	public Event(String desc) {
		countID++;
		this.desc = desc;
		this.eventID = countID;
		this.createdAt = System.currentTimeMillis();
	}
	
	public boolean addUser(User x) {
		invited.add(x);
		usercount++;
		return true;
	}
 }