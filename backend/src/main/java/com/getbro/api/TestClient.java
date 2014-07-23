/*package minirestwebservice;

import com.sun.jersey.api.client.*;

import com.sun.jersey.api.json.JSONConfiguration;
public class TestClient
{
   public static void main( String[] args )
   {
   	   WebResource wrs;
   //User erzeugen
   for(int i = 1;i<6;i++)
   {
   	wrs = Client.create().resource( "http://localhost:4434/test/regUser?name=Max&age="+Integer.toString(i*10) );	
   	System.out.println("User erstellt: " + wrs.accept( "application/json" ).get( String.class ) );
   }
   //Events erzeugen

   	wrs = Client.create().resource( "http://localhost:4434/test/createEvent?desc=heute20:00saufen@loco" );	
   	System.out.println("Event erstellt: " + wrs.accept( "application/json" ).get( String.class ) );
   	wrs = Client.create().resource( "http://localhost:4434/test/createEvent?desc=morgen19:00tennisspielen" );	
   	System.out.println("Event erstellt: " + wrs.accept( "application/json" ).get( String.class ) );
   	
      // add user
      wrs = Client.create().resource( "http://localhost:4434/test/addUsertoEvent?eventID=1&userID=1" );	
   	System.out.println("User zu Event hinzugefuegt: " + wrs.accept( "application/json" ).get( String.class ) );
   //Events anzeigen
      	wrs = Client.create().resource( "http://localhost:4434/test/getEvents" );	
   	System.out.println("Alle Events anzeigen: " +  wrs.accept( "application/json" ).get( String.class ) );
   //User anzeigen
      	wrs = Client.create().resource( "http://localhost:4434/test/getUsers" );	
   	System.out.println("Alle User anzeigen " + wrs.accept( "application/json" ).get( String.class ) );
   	
   	
   	//POST UserTest
   	User user = new User("Alex",24);
   	WebResource webResource = Client.create().resource("http://localhost:4434/test/postUser");
   	ClientResponse response = webResource.accept("application/json").type("application/json").post(ClientResponse.class, user);
   	if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
   	String output = response.getEntity(String.class);
   	System.out.println("Server response .... \n");
   	System.out.println(output);
   	
   	//POST EventTest
   	Event event = new Event("gesternmorgenskiafhreninschladming1900");
   	webResource = Client.create().resource("http://localhost:4434/test/postEvent");
   	response = webResource.accept("application/json").type("application/json").post(ClientResponse.class, event);
   	if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
   	output = response.getEntity(String.class);
   	System.out.println("Server response .... \n");
   	System.out.println(output);
   	
   	
//Events anzeigen
      	wrs = Client.create().resource( "http://localhost:4434/test/getEvents" );	
   	System.out.println("Alle Events anzeigen: " +  wrs.accept( "application/json" ).get( String.class ) );
   //User anzeigen
      	wrs = Client.create().resource( "http://localhost:4434/test/getUsers" );	
   	System.out.println("Alle User anzeigen " + wrs.accept( "application/json" ).get( String.class ) );
   }
}*/