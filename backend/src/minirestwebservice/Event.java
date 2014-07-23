package minirestwebservice;

import javax.xml.bind.annotation.*;
import java.util.*;  
@XmlRootElement
public class Event {
	
	public String desc;
	public int eventID;
	public static int countID=0;
        public List<User> invited = new LinkedList<User>();
	private int usercount=0;
        
	public Event() {} 
   
	public Event(String desc) {
		countID++;
		this.desc = desc;
		this.eventID = countID;
	}
	
	public boolean addUser(User x) {
		invited.add(x);
		usercount++;
		return true;
	}
 }