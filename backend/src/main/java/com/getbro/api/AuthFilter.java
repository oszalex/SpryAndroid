
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
@Provider
public class AuthFilter implements ContainerRequestFilter
{
	private static final int BUFFER_SIZE = 4 * 1024;

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
   public void filter( ContainerRequestContext requestContext ) throws IOException //COntainerrequest anstatt containerrequestcntext
   {
        String method = requestContext.getMethod();
        String path = requestContext.getUriInfo().getPath();
        
      //Ohne Authentifizierung erlaubt
       if(method.equals("GET") && (path.equals("application.wadl") || path.equals("application.wadl/xsd0.xsd"))){
       	    System.out.println("Wadl");   
            return;
        }
        if(method.equals("POST") && (path.equals("users") || path.equals("application.wadl/xsd0.xsd"))){
            System.out.println("Usercreation - No Login needed");
            return;
        }
        final String authHeader = requestContext.getHeaderString( HttpHeaders.AUTHORIZATION );
        String json = inputStreamToString(requestContext.getEntityStream(),"UTF-8");
        System.out.println("JSON " +json);
        // phonenumber:signature
        //wir signieren das Datum im header Date
      if ( authHeader == null ){
         requestContext.abortWith(Response
                    .status(Response.Status.UNAUTHORIZED)
                    .entity("User cannot access the resource.")
                    .build());
      }
      else{
         final String withoutBasic = authHeader.replaceFirst( "[Bb]asic ", "" );
	 final String userColonPass = new String(Base64.decode( withoutBasic ));
	 final String[ ] asArray = userColonPass.split( ":" );
 
	 if ( asArray.length == 2 ){
            final String username = asArray[ 0 ];
            final String signature = asArray[ 1 ];
                    System.out.println("User " +username);
                    System.out.println("Pass " +signature);
            // code to check username and password
         }
	 else{
            requestContext.abortWith(Response
                    .status(Response.Status.UNAUTHORIZED)
                    .entity("User cannot access the resource.")
                    .build());
         }
      }
   }
}