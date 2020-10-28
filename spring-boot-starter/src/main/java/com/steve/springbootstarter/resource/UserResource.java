package com.steve.springbootstarter.resource;

import java.util.List;

import com.steve.springbootstarter.model.User;
import com.steve.springbootstarter.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
  path = "/api/v1/users"
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

  
}
