package com.steve.springbootstarter.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.steve.springbootstarter.dao.UserDao;
import com.steve.springbootstarter.model.User;
import com.steve.springbootstarter.model.User.Gender;

import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {

  private UserDao userDao;

  @Autowired
  public UserService(UserDao userDao) {
    this.userDao = userDao;
  }

  public List<User> getAllUsers(Optional<String> gender) {    
    List<User> users = userDao.selectAllUsers();
    if(!gender.isPresent())
      return users;
    
    try {
      Gender queryGender = User.Gender.valueOf(gender.get().toUpperCase());
      return users.stream()
              .filter(user -> user.getGender().equals(queryGender))
              .collect(Collectors.toList());
    }catch (Exception e){
      throw new IllegalStateException("Invalid Gender", e);
    }
  }

  public Optional<User> getUser(UUID userUid) {
    return userDao.selectUserByUserUid(userUid);
  }

  public int updateUser(User user) {
    Optional<User> optionalUser = getUser(user.getUserUid());
    return optionalUser.isPresent() ? userDao.updateUser(user) : -1;
  }

  public int removeUser(UUID userUid) {
    Optional<User> optionalUser = getUser(userUid);
    
    return optionalUser.isPresent() ? userDao.deleteUserByUserUid(userUid) : -1 ;
  }

  public int insertUser(User user) {
    UUID randomUUID = UUID.randomUUID();
    return userDao.insertUser(randomUUID, User.newUser(randomUUID, user));
  }  
}
