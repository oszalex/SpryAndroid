package com.getbro.api.controller;

import com.getbro.api.ApiStorageWrapper;
import com.getbro.api.AuthFilter;
import com.getbro.api.items.Event;
import com.getbro.api.items.EventInvitation;
import com.getbro.api.items.InvitationStatus;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;


@Path("events")
public class EventController extends ApiStorageWrapper {
    private final static Logger Log = Logger.getLogger(AuthFilter.class.getName());

    public EventController() {
    }

    /**
     * Get all events from current user
     *
     * @param request
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Event> getEvents(@Context HttpServletRequest request) {
        List<Event> visibleevents = new LinkedList<Event>();

        //FIXME: (Long) request.getAttribute("username"); does not work
        Long userId = 4369911602033L;

        Log.info("/events called: list all events!");

        for (Event e : events.values()) {
            if(eventIsVisible(e, userId))
                visibleevents.add(e);
        }
        return visibleevents;
    }

    /**
     * get single event
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{eventID:[a-z0-9]+}")
    public Event getEvent(@PathParam("eventID") String eventID) {
        Event e = events.get(Integer.parseInt(eventID) - 1);

        //FIXME: (Long) request.getAttribute("username"); does not work
        Long userId = 4369911602033L;

        if(!eventIsVisible(e, userId))
            throw new NotAuthorizedException("Not allowed to view this event");

        return e;
    }

    /**
     * create new event
     *
     * @param headers
     * @param event
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postJsonEvent(@Context HttpHeaders headers, Event event) {
        Event x = new Event(event);
        x.setCreatorId(4369911602033L);
        events.put(x.getId(), x);

        Log.info("Event " + x.getId());
        return Response.status(200).entity(event).build();
    }

    /**
     * invite user to specific event
     *
     * @param userId
     * @param eventId
     * @param json
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{eventID:[a-z0-9]+}/{userId:[0-9]+}")
    public EventInvitation addUsertoEvent(@PathParam("userID") String userId, @PathParam("eventId") String eventId, String json) {
        System.out.println("Fuege Event " + eventId + " den User " + userId + " hinzu");
        JSONObject obj = new JSONObject(json);
        long userIdx = Long.parseLong(obj.getString("userId"));
        long eventIdx = Long.parseLong(obj.getString("eventId"));
        long invitorIdx = Long.parseLong(obj.getString("phonenumber"));

        return events.get(eventId).invite(userIdx, invitorIdx);
    }


    /**
     * check if event should be visible for specific user
     *
     * @param e
     * @param user_id
     * @return
     */
    private boolean eventIsVisible(Event e, long user_id){
        if (e.getCreatorId() == user_id) {
            return true;
        } else {
            for (long invited_user_id : e.getInvitations()) {
                if (invited_user_id == user_id)
                    return true;
            }
        }
        return false;
    }
}
