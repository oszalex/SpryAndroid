package com.getbro.api;

import java.util.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.*;
import javax.ws.rs.core.Response;


@Path("/users")
public class UserController extends ApiStorangeWrapper{

	/**
	 * get all users
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getUsers() {
		return users;
	}

	/**
	 * get single user by id
	 */
	@GET
	@Path("/{id:[a-z0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUser(@QueryParam("userID") String userID) {
		return users.get(Integer.parseInt(userID));
	}

	/**
	 * add new user (user registration)
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public User addUser(@QueryParam("name") String name, @QueryParam("age") String age) {
		User x = new User(name,Integer.parseInt(age));
		users.add(x);
		return x;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addJsonUser( User user ) {
		String output = user.toString();
		users.add(user);
		return Response.status(200).entity(output).build();
	}
	

	@GET 
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/addUsertoEvent")
	public User addUsertoEvent(@QueryParam("eventID") String eventID, @QueryParam("userID") String userID) {
		User x = users.get(Integer.parseInt(userID)-1);
		events.get(Integer.parseInt(eventID)-1).addUser(x);
		return x;
	}
	
}
 

