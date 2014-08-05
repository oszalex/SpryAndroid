
package com.getbro.api;

import java.util.*;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.*;
import java.math.BigInteger;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import com.sun.jersey.core.util.Base64;
import org.glassfish.jersey.server.ContainerRequest;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.json.*;
import java.io.ByteArrayInputStream;
@Provider
//@UserAuthorization
public class AuthFilter implements ContainerRequestFilter
{
	private static final int BUFFER_SIZE = 4 * 1024;
	private static final String charset = "UTF-8";
	
	public static String inputStreamToString(InputStream inputStream, String charsetName)
        throws IOException {
        	StringBuilder builder = new StringBuilder();
        	InputStreamReader reader = new InputStreamReader(inputStream, charsetName);
        	char[] buffer = new char[BUFFER_SIZE];
        	int length;
        	while ((length = reader.read(buffer)) != -1) {
        		builder.append(buffer, 0, length);
        	}
        	return builder.toString();
        }
   @Override
   public void filter( ContainerRequestContext requestContext ) throws IOException
   {
        String method = requestContext.getMethod();
        String path = requestContext.getUriInfo().getPath();
        final String date = requestContext.getHeaderString( HttpHeaders.DATE );
        String authHeader = requestContext.getHeaderString( HttpHeaders.AUTHORIZATION );
        if ( authHeader == null ){
   	 	 requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("User cannot access the resource.").build());
   	 	 System.out.println("Abort 0");
        }
        String[] credentials = decodeheader(authHeader);
        //System.out.println("IS:" + credentials[0] + " " + credentials[1]);
        if(requestContext.hasEntity()){
        	//Aendern des JSON, userID aus header in Json kopieren
        	InputStream is;
        	if((is= editJson(requestContext.getEntityStream(),credentials[0]))==null){
        		requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("Invalid Request.").build());
        		System.out.println("Abort 4");
        	}
        	requestContext.setEntityStream(is);
        }else{
        	requestContext.abortWith(Response
                    .status(Response.Status.UNAUTHORIZED)
                    .entity("Invalid Request.")
                    .build());
                System.out.println("Abort 1");
        }
       /* if(method.equals("GET") && (path.equals("application.wadl") || path.equals("application.wadl/xsd0.xsd"))){
       	    System.out.println("Wadl");   
            return;
        }*/ 
        if(method.equals("POST") && path.equals("users")){
            System.out.println("Usercreation - No Login needed");
            return;
        }
        if( authorize(credentials[0],credentials[1], date)) return;
        else{
        	requestContext.abortWith(Response
                    .status(Response.Status.UNAUTHORIZED)
                    .entity("Invalid Request.")
                    .build());
                System.out.println("Abort 2");
        }
   }
   public static boolean authorize(String userId, String signature, String date)
   {
   	   
   	   try{
   	   	   User x = ApiStorageWrapper.users.get(Long.parseLong(userId));
                    // signatur entschluesseln
                    signature = x.decode(signature);
                    System.out.println("authorize " + x.getId() + " " + signature + " " + date);
                    // Vergleich mit HTTP Datum
                  if(signature.compareTo(date) > 0){
                  	  System.out.println("Signatur passt ");
                  	  return true;
                  } 
                  else{
                  	  System.out.println("Signatur passt nicht");
                  	  return true;
                  }
           }
           catch(Exception e){
   		e.printStackTrace();
   		return false;
   	}
   }
   public static InputStream editJson(InputStream is, String userId){
   	JSONObject jason;
   	   try{
   		String json = inputStreamToString(is,charset);
   		jason = new JSONObject(json);
   		jason.put("userId",userId);
   		return new ByteArrayInputStream( jason.toString().getBytes( charset ) );
   	}
   	catch(Exception e)
   	{
   		e.printStackTrace();
   		return null;
   	}
	
   }
   public static String[] decodeheader(String authHeader)
   {
   	final String withoutBasic = authHeader.replaceFirst( "[Bb]asic ", "" );
	final String userColonPass = new String(Base64.decode( withoutBasic ));
	final String[ ] asArray = userColonPass.split( ":" );
	 if ( asArray.length == 2 ){
            final String username = asArray[ 0 ];
            final String signature = asArray[ 1 ];
                    System.out.println("User " +username);
              //      System.out.println("Pass " +signature); 
         }
	 else{
	 	 return null;
         }
         return asArray;
   }
}