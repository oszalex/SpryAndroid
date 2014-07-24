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

import java.math.BigInteger;

@Path("events")
public class EventController extends ApiStorangeWrapper{

    public EventController(){

        if(users.size() == 0){

            users.add(new User("chris", 22));
            users.add(new User("alex", 433));

            events.add(new Event("me #hunger #essen @vapiano now!"));
            events.add(new Event("kino heute @apollo 18:00 +chris +diana"));


            Event e1 = new Event("rammelrudel morgen @alexgarten #public");
            e1.invite(new EventInvitation(0, BigInteger.valueOf(0), BigInteger.valueOf(0), InvitationStatus.INVITED));
            
            events.add(e1);

        }
        
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
    @Path("{id}")
    public Event getEvent(@QueryParam("id") String id) {
        return events.get(Integer.parseInt(id));
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
