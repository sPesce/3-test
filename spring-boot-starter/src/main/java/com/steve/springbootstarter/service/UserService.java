package com.steve.springbootstarter.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.steve.springbootstarter.dao.UserDao;
import com.steve.springbootstarter.model.User;
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

  public List<User> getAllUsers() {
    return userDao.selectAllUsers();
  }

  public Optional<User> getUser(UUID userUid) {
    return userDao.selectUserByUserUid(userUid);
  }

  public int updateUser(User user) {
    Optional<User> optionalUser = getUser(user.getUserUid());
    if(optionalUser.isPresent()){
      return userDao.updateUser(user);
    }
    return  -1;
  }

  public int removeUser(UUID userUid) {
    Optional<User> optionalUser = getUser(userUid);
    if(optionalUser.isPresent())
    {
      return userDao.deleteUserByUserUid(userUid);
    }
    return -1;
  }

  public int insertUser(User user) {
    UUID randomUUID = UUID.randomUUID();
    user.setUserUid(randomUUID);
    return userDao.insertUser(randomUUID, user);
  }  
}
