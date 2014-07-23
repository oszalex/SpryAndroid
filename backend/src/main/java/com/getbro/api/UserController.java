package com.getbro.api;

import java.util.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.*;
import javax.ws.rs.core.Response;


@Path("/users")
public class UserController extends ApiStorangeWrapper{

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getUsers() {
		return users;
	}


	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/regUser")
	public User regUser(@QueryParam("name") String name, @QueryParam("age") String age) {
		User x = new User(name,Integer.parseInt(age));
		users.add(x);
		return x;
	}
	
	@GET 
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/user")
	public User getUser(@QueryParam("userID") String userID) {
		return users.get(Integer.parseInt(userID)-1);
	}

	@GET 
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/addUsertoEvent")
	public User addUsertoEvent(@QueryParam("eventID") String eventID, @QueryParam("userID") String userID) {
		User x = users.get(Integer.parseInt(userID)-1);
		events.get(Integer.parseInt(eventID)-1).addUser(x);
		return x;
	}
	
	@POST
	@Path("/postUser")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postUser( User user ) {
		String output = user.toString();
		users.add(user);
		return Response.status(200).entity(output).build();
	}
	
	
	
}
 

