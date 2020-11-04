package com.steve.springbootstarter.resource;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.steve.springbootstarter.model.User;
import com.steve.springbootstarter.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

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

    
  
}