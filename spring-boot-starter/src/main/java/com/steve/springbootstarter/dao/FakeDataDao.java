package com.steve.springbootstarter.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.steve.springbootstarter.model.User;

public class FakeDataDao implements UserDao {

  private static Map<UUID, User> database;

  static {
    database = new HashMap<>();
    UUID userUid = UUID.randomUUID();
    database.put( userUid , new User(
        userUid,
        firstName: "Joe",
        lastName: "Jones",
        Gender.MALE,
        age: 22,
        email: "joe@joe.com"
        ));
  }

  @Override
  public List<User> getAllUsers() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public User getUser(UUID userUid) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int updateUser(User user) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int removeUser(UUID userUid) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int insertUser(User user) {
    // TODO Auto-generated method stub
    return 0;
  }
  
}
