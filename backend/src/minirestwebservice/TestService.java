package minirestwebservice;

import java.util.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.*;
import javax.ws.rs.core.Response;


@Path("/test")
public class TestService
{
	public static List<User> users = new LinkedList<User>();
	public static List<Event> events = new LinkedList<Event>();
	//Users allUsers = new Users();
	
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
	@Path("/createEvent")
	public Event createEvent(@QueryParam("desc") String desc) {
		Event x = new Event(desc);
		events.add(x);
		return x;
	}
	
	@GET 
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getUsers")
	public List<User> getUsers() {
		return users;
	}
	
	@GET 
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getEvents")
	public List<Event> getEvents() {
		return events;
	}
	
	@GET 
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getEvent")
	public Event getEvent(@QueryParam("eventID") String eventID) {
		return events.get(Integer.parseInt(eventID)-1);
	}
	@GET 
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getUser")
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
	
	@POST
	@Path("/postEvent")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postEvent( Event event ) {
		String output = event.toString();
		events.add(event);
		return Response.status(200).entity(output).build();
	}
	
}
 

