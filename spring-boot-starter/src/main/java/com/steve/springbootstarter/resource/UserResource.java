package com.steve.springbootstarter.resource;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.steve.springbootstarter.model.User;
import com.steve.springbootstarter.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
  path = "/api/v1/users"//  <<-----------------------------------
)
public class UserResource {
  
  private UserService userService;

  @Autowired
  public UserResource(UserService userService) {
    this.userService = userService;
  }

  @RequestMapping
  (
    method = RequestMethod.GET
  )
  public List<User> fetchUsers() {
    return userService.getAllUsers();
  }

  @RequestMapping(
    method = RequestMethod.GET,
    path = "{userUid}"
  )
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