
/*package com.getbro.api;

import java.util.*;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.*;
import java.math.BigInteger;

@Provider
@UserAuthorization
public class AuthFilter implements ContainerRequestFilter
{
   @Override
   public void filter( ContainerRequestContext requestContext ) throws IOException
   {
      final String authHeader = requestContext.getHeaderString( HttpHeaders.AUTHORIZATION );
      if ( authHeader == null )
      {
         requestContext.abortWith( Response.status( Status.UNAUTHORIZED )
            .header( HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"realm\"" )
            .entity( "Page requires login." ).build( ) );
      }
      else
      {
         final String withoutBasic = authHeader.replaceFirst( "[Bb]asic ", "" );
	 final String userColonPass = Base64.decodeAsString( withoutBasic );
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
}*/