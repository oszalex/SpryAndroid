package com.getbro.api;

import java.util.*;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("events")
public class EventController extends ApiStorangeWrapper{

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
    @Path("{id:[a-z0-9]+}")
    public Event getEvent(@QueryParam("eventID") String eventID) {
        return events.get(Integer.parseInt(eventID));
    }

    /**
     * add event
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postJsonEvent( Event event ) {
        String output = event.toString();
        events.add(event);
        return Response.status(200).entity(output).build();
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
