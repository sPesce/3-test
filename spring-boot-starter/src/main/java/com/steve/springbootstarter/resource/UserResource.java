package com.steve.springbootstarter.resource;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.websocket.server.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.steve.springbootstarter.dao.UserDao;
import com.steve.springbootstarter.model.User;
import com.steve.springbootstarter.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ch.qos.logback.core.status.Status;

// @RestController
// @RequestMapping(
//   path = "/api/v1/users"//  <<-----------------------------------
// )
public class UserResource {
  
  private UserService userService;

  @Autowired
  public UserResource(UserService userService) {
    this.userService = userService;
  }
  
  @GET
  @Produces(APPLICATION_JSON)                    
  public List<User> fetchUsers(@QueryParam("gender") String gender) {
    return userService.getAllUsers(Optional.ofNullable(gender));    
  }

  @GET
  @Produces(APPLICATION_JSON)
  @Path("{userUid}")
  public Response fetchUser(@PathParam("userUid") UUID userUid) {
    Optional<User> userOptional = userService.getUser(userUid);
    if( userOptional.isPresent())
      return Response.ok(userOptional.get()).build();
    else
      return (
        Response
        .status( Response.Status.NOT_FOUND)
        .entity(new ErrorMessage("user " + userUid + " was not found."))
        .build()
      );
  }

  @POST
  @Consumes(APPLICATION_JSON)
  @Produces(APPLICATION_JSON)
  public Response insertNewUser(@RequestBody User user) {
    int result = userService.insertUser(user);
    return getIntResponseEntity(result);
  }

  @PUT
  @Consumes(APPLICATION_JSON)
  @Produces(APPLICATION_JSON)
  public Response updateUser(@RequestBody User user) {
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
  
  private class ErrorMessage {
    private String message;
  
    public ErrorMessage(String message) {
      this.message = message;
    }
  
    public String getMessage() {
      return this.message;
    }
  
    public void setMessage(String message) {
      this.message = message;
    }
  
    @Override
    public String toString() {
      return "{" +
        " message='" + message + "'" +
        "}";
    }
  }


}