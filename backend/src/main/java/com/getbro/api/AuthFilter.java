
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


@Provider
public class AuthFilter implements ContainerRequestFilter
{
   @Override
   public void filter( ContainerRequestContext requestContext ) throws IOException //COntainerrequest anstatt containerrequestcntext
   {
      final String authHeader = requestContext.getHeaderString( HttpHeaders.AUTHORIZATION );
      System.out.println("Filter");
      //GET, POST, PUT, DELETE, ...
        String method = requestContext.getMethod();
        // myresource/get/56bCA for example
        String path = requestContext.getPath(true);
      //Ohne Authentifizierung erlaubt
       if(method.equals("GET") && (path.equals("application.wadl") || path.equals("application.wadl/xsd0.xsd"))){
            return containerRequest;
        }
        //Usercreate ist einzige erlaubt ohne Authentifizierung
        // phonenumber:signature
        //wir signieren das Datum imheader Date
      if ( authHeader == null ){
        // requestContext.abortWith( Response.header( HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"realm\"" )
          //  .entity( "Page requires login." ).build( ) );
      }
      else
      {
         final String withoutBasic = authHeader.replaceFirst( "[Bb]asic ", "" );
	 final String userColonPass = new String(Base64.decode( withoutBasic ));
	 final String[ ] asArray = userColonPass.split( ":" );
 
	 if ( asArray.length == 2 )
	 {
            final String username = asArray[ 0 ];
            final String password = asArray[ 1 ];
 
            // code to check username and password
         }
	 else
	 {
            // abort with error
         }
      }
   }
}