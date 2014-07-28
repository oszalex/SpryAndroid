package com.getbro.api;

import javax.xml.bind.annotation.*;
import java.util.*;
import java.math.BigInteger;

import java.util.ArrayList;
import java.util.Calendar;
import java.text.SimpleDateFormat;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.text.ParseException;


@XmlRootElement
public class Event {
	private String raw;
	private long eventID;
	private long createdAt;
	private BigInteger creatorID;
	private boolean isPublic;
	private ArrayList<String> tags = new ArrayList<String>();
	private Calendar datetime = Calendar.getInstance();

	private static final String TIME24HOURS_PATTERN = "([01]?[0-9]|2[0-3]):[0-5][0-9]";

	public static int countID=0;
    
    private LinkedList<EventInvitation> invited = new LinkedList<EventInvitation>();
        
	private Event() {}

	public static Event fromString(long creator, String raw){
		Event e = new Event();

		e.creatorID = BigInteger.valueOf(creator);

		String[] words = raw.split(" ");

		/*
		 * search for 24h time definition
		 */
		Pattern pattern = Pattern.compile(TIME24HOURS_PATTERN);
		Matcher matcher = pattern.matcher(raw);


		if(matcher.find()){
			SimpleDateFormat df = new SimpleDateFormat("HH:mm");
			try{
				Date d = df.parse(raw.substring(matcher.start(),matcher.end()));

				e.datetime.setTime(d);
			} catch(ParseException ex){
				//TODO error
			}
		}



		for(String word: words){

			/*
			 * find tags
			 */
			if(word.startsWith("#")){
				e.addTag(word.substring(1));
			}



			/*
			 * find timeinfo
			 */
			switch(word){
				case "tomorrow":
					e.datetime.add(Calendar.DATE, 1);
			}


			
		}

		return e;
	}

	public Event(String raw) {

		if(raw.indexOf("#public") > 0)
			this.isPublic = true;

		countID++;
		this.raw = raw;
		this.eventID = countID;
		this.createdAt = System.currentTimeMillis();
		this.creatorID = new BigInteger("004369911602033");
	}

	public Event(Event event) {
		if(event.getRaw().indexOf("#public") > 0) this.isPublic = true;
		countID++;
		this.raw = event.raw;
		this.eventID = countID;
		this.createdAt = System.currentTimeMillis();
		this.creatorID = new BigInteger("004369911602033");
	}


	@XmlElement(name="raw")
	public String getRaw(){
		return raw;
	}
	public void setRaw(String raw){
		System.out.println("Setraw");
		this.raw = raw;
	}

	private void addTag(String tag){
		tags.add(tag);
	}

	@XmlElement(name="time")
	public long getTime(){
		return datetime.getTimeInMillis();
	}

	@XmlElement(name="createdAt")
	public long getCreatedAt(){
		return  createdAt;
	}

	@XmlElement(name="id")
	public long getId(){
		return eventID;
	}



	public ArrayList<String> getTags(){
		return tags;
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




	//time getter

	public int getDay(){
		return datetime.get(Calendar.DAY_OF_MONTH); 
	}

	public Calendar getDatetime(){
		return datetime;
	}
 }