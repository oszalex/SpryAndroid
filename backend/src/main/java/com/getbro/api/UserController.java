package com.getbro.api;

import java.util.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.*;
import javax.ws.rs.core.Response;

import org.json.*;
@Path("/users")
public class UserController extends ApiStorageWrapper{

	/**
	 * get all users
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getUsers() {
		List<User> visibleusers = new LinkedList<User> ();
		return visibleusers;
	}

	/**
	 * get single user by id
	 */
	 @GET
	 @Produces(MediaType.APPLICATION_JSON)
	 @Path("{userID:[0-9]+}")
	 public User getUser(@PathParam("userID") String userID) {
	 	 return users.get(Integer.parseInt(userID)-1);
	 }

	/**
	 * add new user (user registration)
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addJsonUser( User user ) {
		try{
		System.out.println("Creating New User");
		User x = new User(user);
		if(!users.containsKey(x.getId())) users.put(Long.parseLong(x.getId()),x);
		x.sendConfirmation();
		}
		catch(Exception e)
		{
			e.printStackTrace();	
		}
		return Response.status(200).build();
	}
	@POST
	@Path("{userID:[0-9]+}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response checkJsonUser( String jason, @PathParam("userID") String userID ) {
		try{
		//wie hole ich mir den Code aus JSON??
		JSONObject obj = new JSONObject(jason);
		int activationcode = Integer.parseInt(obj.getString("code"));
		System.out.println("Received Code" + activationcode);
		
		User x = users.get(Long.parseLong(userID));
		//No Such User
		if( x == null) return Response.status(500).entity(x).build();
		if( x.checkActivation(activationcode)) return Response.status(200).entity(x).build();
		else return Response.status(501).entity(x).build();
		}
		catch(Exception e)
		{
			e.printStackTrace();	
		}
		return Response.status(500).build();
		//No such user exception
	}
	

	@GET 
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/addUsertoEvent")
	public User addUsertoEvent(String jason) {
		User x = users.get(Integer.parseInt(userID)-1);
		//events.get(Integer.parseInt(eventID)-1).addUser(x);
		return x;
	}
	
}
 

