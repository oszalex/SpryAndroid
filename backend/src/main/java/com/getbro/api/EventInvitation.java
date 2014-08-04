package com.getbro.api;

import javax.xml.bind.annotation.*;
import java.util.*;
import java.math.BigInteger;

@XmlRootElement
public class EventInvitation {
	private long eventId;
	private long userID;
	private long inviterID;
	private InvitationStatus status;

	public EventInvitation(long eventId, long userID, 
		long inviter, InvitationStatus status){
		this.eventId = eventId;
		this.userID = userID;
		this.inviterID = inviterID;
		this.status = status;
	}

	@XmlElement(name="eventId")
	public long getEventId(){
		return eventId;
	}

	@XmlElement(name="guestId")
	public long getUserId(){
		return userID;
	}

	@XmlElement(name="inviterId")
	public long getInviterID(){
		return inviterID;
	}

	@XmlElement(name="status")
	public InvitationStatus getStatus(){
		return status;
	}
}