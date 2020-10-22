package com.steve.springbootstarter.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.steve.springbootstarter.model.User;
import com.steve.springbootstarter.model.User.Gender;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FakeDataDaoTest {

  private FakeDataDao fakeDataDao;

  @BeforeEach
  public void setUp() throws Exception {
    fakeDataDao = new FakeDataDao();
  }

  @Test
  public void shouldSelectAllUsers() throws Exception {
    List<User> users = fakeDataDao.selectAllUsers();
    assertThat(users).hasSize(1);
    User user = users.get(0);
    assertThat(user.getAge()).isEqualTo(22);
    assertThat(user.getFirstName()).isEqualTo("Joe");
    assertThat(user.getLastName()).isEqualTo("Jones");
    assertThat(user.getGender()).isEqualTo(Gender.MALE);
    assertThat(user.getEmail()).isEqualTo("Joe.jones@gmail.com");
    assertThat(user.getUserUid()).isNotNull();
  }
  
  @Test
  public void shouldSelectUserByUserUid() throws Exception {
    UUID annaUid = UUID.randomUUID();
    User anna = new User(annaUid , "Anna", "Montana", Gender.FEMALE, 30, "anna@gmail.com");
    fakeDataDao.insertUser(annaUid,anna);
    assertThat(fakeDataDao.selectAllUsers()).hasSize(2);
    
    Optional<User> optAnna = fakeDataDao.selectUserByUserUid(annaUid);
    assertThat(optAnna.isPresent()).isTrue();
    assertThat(optAnna.get()).isEqualToComparingFieldByField(anna);
    
  }
  
  @Test
  public void shouldNotSelectUserByUserUid() throws Exception {
    Optional<User> optAnna = fakeDataDao.selectUserByUserUid(UUID.randomUUID());
    assertThat(optAnna.isPresent()).isFalse();
    
  }
  
  @Test
  public void  shouldUpdateUser() throws Exception {
    UUID joeID = fakeDataDao.selectAllUsers().get(0).getUserUid();
    User newJoe = new User(joeID, "Anna", "Montana", Gender.FEMALE, 30, "anna@gmail.com");
    fakeDataDao.updateUser(newJoe);
    
    Optional<User> user = fakeDataDao.selectUserByUserUid(joeID);
    assertThat(user.isPresent()).isTrue();
    
    assertThat(fakeDataDao.selectAllUsers()).hasSize(1);
    assertThat(user.get()).isEqualToComparingFieldByField(newJoe);
    
  }
  
  @Test
  public void shouldDeleteUserByUserUid() throws Exception {
    UUID joeID = fakeDataDao.selectAllUsers().get(0).getUserUid();
    fakeDataDao.deleteUserByUserUid(joeID);
    assertThat(fakeDataDao.selectUserByUserUid(joeID).isPresent()).isFalse();
    assertThat(fakeDataDao.selectAllUsers()).isEmpty();

  }
  
  @Test
  public void shouldInsertUser() throws Exception {
    UUID userUid = UUID.randomUUID();
    User newJoe = new User(userUid, "Anna", "Montana", Gender.FEMALE, 30, "anna@gmail.com");
    
    fakeDataDao.insertUser(userUid, newJoe);
   
    List<User> users = fakeDataDao.selectAllUsers();
    
    assertThat(users).hasSize(2);
    assertThat(fakeDataDao.selectUserByUserUid(userUid).get())
    .isEqualToComparingFieldByField(newJoe);
  }
  
}
