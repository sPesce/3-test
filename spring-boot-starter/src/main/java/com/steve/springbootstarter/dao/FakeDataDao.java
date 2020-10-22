package com.steve.springbootstarter.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.steve.springbootstarter.model.User;
import com.steve.springbootstarter.model.User.Gender;

import org.springframework.stereotype.Repository;

@Repository
public class FakeDataDao implements UserDao {

  private static Map<UUID, User> database;

  static {
    database = new HashMap<>();
    UUID userUid = UUID.randomUUID();
    database.put( userUid , new User(
        userUid,
        "Joe",
        "Jones",
        Gender.MALE,
        22,
        "Joe.jones@gmail.com"    
        ));
  }

  @Override
  public List<User> selectAllUsers() {
    return new ArrayList<>(database.values());
  }

  @Override
  public Optional<User> selectUserByUserUid(UUID userUid) {
    return Optional.ofNullable(database.get(userUid));
  }

  @Override
  public int updateUser(User user) {
    database.put(user.getUserUid() ,user);
    return  1;
  }

  @Override
  public int deleteUserByUserUid(UUID userUid) {
    database.remove(userUid);
    return 1;
  }

  @Override
  public int insertUser(UUID userUid, User user) {
    database.put(userUid, user);
    return 1;
  }
  
}
