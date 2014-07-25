package com.getbro.api;

import java.util.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.*;
import javax.xml.bind.*;
import com.sun.jersey.api.json.*;
//import com.sun.jersey.api.json.*;
@Provider
public class JAXBContextResolver implements ContextResolver<JAXBContext> {
   
       private JAXBContext context;
       private Class[] types = {User.class};
   
       public JAXBContextResolver() throws Exception {
           this.context = 
   	  new JSONJAXBContext( JSONConfiguration.natural().build(), types);
     }
 
      public JAXBContext getContext(Class<?> objectType) {
          for (Class type : types) {
              if (type == objectType) {
                  return context;
              }
 }
          return null;
      }
  }
