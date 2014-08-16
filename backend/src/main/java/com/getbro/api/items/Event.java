package com.getbro.api.items;

import javax.xml.bind.annotation.*;
import java.util.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.text.SimpleDateFormat;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.text.ParseException;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Event implements Comparable<Event> {
    @XmlElement(required=true)
    private String raw;
    @XmlElement(required=true)
    private long eventId;
    @XmlElement(required=true)
    private long createdAt;
    private long creatorId;
    private boolean isPublic;
    private ArrayList<String> tags = new ArrayList<String>();
    private LinkedList<EventInvitation> invited = new LinkedList<EventInvitation>();

    private Calendar datetime = Calendar.getInstance();

    private static final String TIME24HOURS_PATTERN = "([01]?[0-9]|2[0-3]):[0-5][0-9]";

    public static int countID = 0;



    private Event() {
        eventId = countID++;
    }

    public static Event fromString(long creator, String raw) {
        Event e = new Event();
        e.setRaw(raw);
        e.setCreatorId(creator);

		/*
         * search for 24h time definition
		 */
        Pattern pattern = Pattern.compile(TIME24HOURS_PATTERN);
        Matcher matcher = pattern.matcher(raw);


        if (matcher.find()) {
            SimpleDateFormat df = new SimpleDateFormat("HH:mm");
            try {
                Date d = df.parse(raw.substring(matcher.start(), matcher.end()));

                e.datetime.setTime(d);
            } catch (ParseException ex) {
                //TODO error
            }
        }

        /*
         * process tags and special words
         */

        String[] words = raw.split(" ");
        for (String word : words) {

			// find tags
            if (word.startsWith("#"))
                e.addTag(word.substring(1));

			 // find timeinfo
            switch (word) {
                case "tomorrow":
                case "morgen":
                    e.datetime.add(Calendar.DATE, 1);
                    break;
                case "nextweek":
                    e.datetime.add(Calendar.DATE, 7);
                    break;
            }

        }

        if(e.tags.contains("public"))
            e.isPublic = true;

        return e;
    }

    public Event(String raw) {
        if (raw.indexOf("#public") > 0)
            this.isPublic = true;

        this.raw = raw;
        this.eventId = countID++;
        this.createdAt = System.currentTimeMillis();
    }

    /**
     * copy constructor
     *
     * @param event
     */
    public Event(Event event) {
        this.isPublic = event.isPublic;
        this.raw = event.raw;
        this.eventId = countID;
        this.createdAt = event.createdAt;
        this.creatorId = event.creatorId;
    }


    //@XmlElement(name = "raw")
    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    private void addTag(String tag) {
        tags.add(tag);
    }

    //@XmlElement(name = "time")
    public long getTime() {
        return datetime.getTimeInMillis();
    }

    //@XmlElement(name = "createdAt")
    public long getCreatedAt() {
        return createdAt;
    }

    //@XmlElement(name = "id")
    public long getId() {
        return eventId;
    }

    public long setCreatorId(long id) {
        return creatorId = id;
    }


    public ArrayList<String> getTags() {
        return tags;
    }

    //@XmlElement(name = "creatorId")
    public long getCreatorId() {
        return creatorId;
    }

    //@XmlElement(name = "invitations")
    public List<Long> getInvitations() {
        List<Long> users = new LinkedList<Long>();

        for (EventInvitation i : invited) {
            users.add(i.getUserId());
        }
        return users;
    }

    //@XmlElement(name = "public")
    public boolean getIsPublic() {
        return isPublic;
    }


    public EventInvitation invite(User u, User inviter) {
        EventInvitation i = new EventInvitation(this.getId(), u.getId(), inviter.getId(), InvitationStatus.INVITED);
        invited.add(i);

        return i;
    }

    public EventInvitation invite(long u, long inviter) {
        EventInvitation i = new EventInvitation(this.getId(), u, inviter, InvitationStatus.INVITED);
        invited.add(i);

        return i;
    }


    //time getter

    public int getDay() {
        return datetime.get(Calendar.DAY_OF_MONTH);
    }

    public Calendar getDatetime() {
        return datetime;
    }

    @Override
    public int compareTo(Event o) {
        return this.datetime.compareTo(o.datetime);
    }
}