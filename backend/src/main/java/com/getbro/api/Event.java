package com.getbro.api;

import javax.xml.bind.annotation.*;
import java.util.*;
import java.math.BigInteger;


@XmlRootElement
public class Event {
	private String desc;
	private long eventID;
	private long createdAt;
	private BigInteger creatorID;
	private boolean isPublic;

	public static int countID=0;
    
    private LinkedList<EventInvitation> invited = new LinkedList<EventInvitation>();
        
	private Event() {}

	public Event(String desc) {

		if(desc.indexOf("#public") > 0)
			this.isPublic = true;

		countID++;
		this.desc = desc;
		this.eventID = countID;
		this.createdAt = System.currentTimeMillis();
		this.creatorID = new BigInteger("004369911602033");
	}
	public Event(Event event) {

		if(desc.indexOf("#public") > 0)
			this.isPublic = true;
		countID++;
		this.desc = event.desc;
		this.eventID = countID;
		this.createdAt = System.currentTimeMillis();
		this.creatorID = new BigInteger("004369911602033");
	}


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
	public long getId(){
		return eventID;
	}

	@XmlElement(name="creatorId")
	public BigInteger getCreatorId(){
		return creatorID;
	}

	@XmlElement(name="invitations")
	public List<BigInteger> getInvitations(){
		List<BigInteger> users = new LinkedList<BigInteger>();

		for (EventInvitation i : invited){
			users.add(i.getUserId());
		}
		return users;
	}

	@XmlElement(name="public")
	public boolean getIsPublic(){
		return isPublic;
	}

	
	public void invite(EventInvitation i) {
		invited.add(i);
	}
 }