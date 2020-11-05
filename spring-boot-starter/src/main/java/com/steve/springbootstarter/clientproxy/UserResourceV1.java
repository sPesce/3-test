package com.steve.springbootstarter.clientproxy;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.steve.springbootstarter.model.User;

import org.jboss.resteasy.annotations.jaxrs.PathParam;

public interface UserResourceV1 {//==============================================
  @GET
  @Produces(APPLICATION_JSON)
  List<User> fetchUsers(@QueryParam("gender") String gender);
  //----------------------------------------------------------------

  @GET
  @Produces(APPLICATION_JSON)
  @Path("{id}")
  Response fetchUser(@PathParam("id") UUID id);
  //----------------------------------------------------------------
  
  @POST
  @Consumes(APPLICATION_JSON)
  @Produces(APPLICATION_JSON)
  Response insertNewUser( User user);
  //----------------------------------------------------------------
  
  @PUT
  @Consumes(APPLICATION_JSON)
  @Produces(APPLICATION_JSON)
  Response updateUser( User user); 
  //----------------------------------------------------------------
  
  @DELETE
  @Produces(APPLICATION_JSON)
  @Path("{userUid}")
  Response deleteUser(@PathParam("userUid") UUID userUid);  
  //----------------------------------------------------------------
}//============================================================================
