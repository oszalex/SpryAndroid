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
    public List<Event> getEvents() {
        return events;
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
        events.add(new Event(event));
        System.out.println("hier");
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
