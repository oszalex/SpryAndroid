package com.getbro.api;

import java.util.*;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.math.BigInteger;

@Path("events")
public class EventController extends ApiStorageWrapper{

    public EventController(){
    }

    /**
     * list all events
     */
    @GET 
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Event> getEvents(long userId) {
    	List<Event> visibleevents = new LinkedList<Event> ();
    	/*//Alle Elemente in Hashmap durchlaufen
    	for(Event x: events){	
    		//User ID ist Ersteller des Events
    		if(x.getCreatorId() == userId) visibleevents.add(x);
    		else{
    			//User ist eingeladen
    			List<User> invites = x.getInvitations();
    			for(User y : invites){
    				if(y.getID() == userId){
    					visibleevents.add(x);
    					break;
    				}	
    			}
    		}
    	}*/
        return visibleevents;
    }
    
    /**
     * get single event
     */
     @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{eventID:[a-z0-9]+}")
    public Event getEvent(@PathParam("eventID") String eventID) {
        return events.get(Integer.parseInt(eventID)-1);
    }
    /**
     * add event
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postJsonEvent( Event event ) {
       // System.out.println("Testevent: " + event);
     //   System.out.println("Testevent2: " +(String) event);
    	Event x = new Event(event);
        events.put(x.getId(),x);
        System.out.println("Event "+ x.getId() +" added");
        return Response.status(200).entity(event).build();
    }

    /**
     * warum eine zweite Methode zum hinzufügen?
     */

    /*
    @POST 
    @Produces(MediaType.APPLICATION_JSON)
    public Event createEvent(@QueryParam("desc") String desc) {
        Event x = new Event(desc);
        events.add(x);
        return x;
    }
    */
}
