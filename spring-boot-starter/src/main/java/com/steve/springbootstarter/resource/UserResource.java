package com.steve.springbootstarter.resource;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.ws.rs.QueryParam;

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
 
  @RequestMapping(                            
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE
  )//GET all users                          
  public List<User> fetchUsers(@QueryParam("gender") String gender) {
    return userService.getAllUsers(Optional.ofNullable(gender));    
  }

 
  @RequestMapping(              
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE,
    path = "{userUid}"        
  )//GET one user
  public ResponseEntity<?> fetchUser(@PathVariable("userUid") UUID userUid) {
    Optional<User> userOptional = userService.getUser(userUid);
    if( userOptional.isPresent())
      return ResponseEntity.ok(userOptional.get());
    else
      return (
        ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(new ErrorMessage("user " + userUid + " was not found."))
      );
  }

 
  @RequestMapping(
    method = RequestMethod.POST,                
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
  )//post one user (add a new user)                                         
  public ResponseEntity<Integer> insertNewUser(@RequestBody User user) {
    int result = userService.insertUser(user);
    return getIntResponseEntity(result);
  }

  @RequestMapping(
    method = RequestMethod.PUT,
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<Integer> updateUser(@RequestBody User user) {
    int result = userService.updateUser(user);
    return getIntResponseEntity(result);
  }
  
  @RequestMapping(
    method = RequestMethod.DELETE,
    path = "{userUid}",
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<Integer> deleteUser(@PathVariable("userUid") UUID userUid) {
    int result = userService.removeUser(userUid);
    return getIntResponseEntity(result);
  }

  
  //helpers ------------------------------------------------------------------- 
  private ResponseEntity<Integer> getIntResponseEntity(int result) {
    return result == 1 
    ? ResponseEntity
      .ok()
      .build() 
    : ResponseEntity
      .badRequest()
      .build();
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