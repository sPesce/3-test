package com.steve.springbootstarter.resource;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.steve.springbootstarter.model.User;
import com.steve.springbootstarter.service.UserService;

import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.UUID;

import org.springframework.web.bind.annotation.RequestBody;


@Component
@Path("/api/v1/users")
public class UserResourceResteasy {

  private UserService userService;

  @Autowired
  public UserResourceResteasy(UserService userService)
  {
    this.userService = userService;
  }

  @GET
  @Produces(APPLICATION_JSON)
  public List<User> fetchUsers(@QueryParam("gender") String gender) {
    return userService.getAllUsers(Optional.ofNullable(gender));  
  }

  @GET
  @Produces(APPLICATION_JSON)
  @Path("{id}")
  public Response fetchUser(@PathParam("id") UUID id) {
    System.out.println("User UID: " + id);
    Optional<User> userOptional = userService.getUser(id);
    if( userOptional.isPresent())
      return Response.ok(userOptional.get()).build();
    else
      return (
        Response
        .status( Response.Status.NOT_FOUND)
        .entity(new ErrorMessage("user " + id + " was not found."))
        .build()
      );
  }

  @POST
  @Consumes(APPLICATION_JSON)
  @Produces(APPLICATION_JSON)
  public Response insertNewUser( User user) {
    int result = userService.insertUser(user);
    return getIntResponseEntity(result);
  }

  @PUT
  @Consumes(APPLICATION_JSON)
  @Produces(APPLICATION_JSON)
  public Response updateUser( User user) {
    int result = userService.updateUser(user);
    return getIntResponseEntity(result);
  } 
  
  @DELETE
  @Produces(APPLICATION_JSON)
  @Path("{userUid}")
  public Response deleteUser(@PathParam("userUid") UUID userUid) {
    int result = userService.removeUser(userUid);
    return getIntResponseEntity(result);
  }

  
  //helpers ------------------------------------------------------------------- 
  private Response getIntResponseEntity(int result) {
    return result == 1 
    ? Response.ok().build() 
    : Response.status(Response.Status.BAD_REQUEST).build();
  } 
  }