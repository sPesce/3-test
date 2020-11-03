package com.steve.springbootstarter.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.google.common.collect.ImmutableList;
import com.steve.springbootstarter.dao.FakeDataDao;
import com.steve.springbootstarter.model.User;
import com.steve.springbootstarter.model.User.Gender;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.handler.UserRoleAuthorizationInterceptor;


public class UserServiceTest {

  @Mock
  private FakeDataDao fakeDataDao;
  
  private UserService userService;



	@BeforeEach
	public void setup() throws Exception{
    MockitoAnnotations.initMocks( this );
		userService = new UserService(fakeDataDao);
	}

	@Test
	public void shouldGetAllUsers() {
    UUID annaUserUid = UUID.randomUUID();
    User anna = new User(annaUserUid , "Anna", "Montana", Gender.FEMALE, 30, "anna@gmail.com");

    ArrayList<User> ls = new ArrayList<>();
    ls.add(anna);

    ImmutableList<User> users = ImmutableList.copyOf(ls);

    given(fakeDataDao.selectAllUsers()).willReturn(users);
		List<User> actualValue = userService.getAllUsers(Optional.empty());
    
    assertThat(actualValue).hasSize(1);
    User testUsr = actualValue.get(0);
    
    assertAnnaFields(testUsr);
	}

	@Test
	public void shouldGetUser() {
    UUID annaUUID = UUID.randomUUID();
    User anna = new User(annaUUID , "Anna", "Montana", Gender.FEMALE, 30, "anna@gmail.com");
    
    given(fakeDataDao.selectUserByUserUid(annaUUID)).willReturn(Optional.of(anna));
    
    
    Optional<User> actualValue = userService.getUser(annaUUID);
    
    assertThat(actualValue.isPresent()).isTrue();
    User testUsr = actualValue.get();
    
    assertAnnaFields(testUsr);
    
	}
  
  @Test
  public void shouldGetAllUsersByGender() throws Exception {
    UUID annaUserUid = UUID.randomUUID();
    User anna = new User(annaUserUid , "Anna", "Montana", Gender.FEMALE, 30, "anna@gmail.com");
  UUID joeUUID = UUID.randomUUID();
  User joe = new User(joeUUID , "Joe", "Jonesey", Gender.MALE, 32, "joe@gmail.com");
  
  ArrayList<User> ls = new ArrayList<>();
  ls.add(anna);
  ls.add(joe);
  
  ImmutableList<User> users = ImmutableList.copyOf(ls);
   
  given(fakeDataDao.selectAllUsers()).willReturn(users);

  List<User> filteredUsers = userService.getAllUsers(Optional.of("female"));
  assertThat(filteredUsers).hasSize(1);

  assertAnnaFields(filteredUsers.get(0));

}

  @Test
  public void shouldThrowExceptionWhenGenderInvalid() throws Exception {
    assertThatThrownBy(() -> userService.getAllUsers(Optional.of("sdsadas")))
    .isInstanceOf(IllegalStateException.class)
    .hasMessageContaining("Invalid Gender");
  }

	@Test
	public void shouldUpdateUser() {
    UUID annaUUID = UUID.randomUUID();
    User anna = new User(annaUUID , "Anna", "Montana", Gender.FEMALE, 30, "anna@gmail.com");

    given(fakeDataDao.selectUserByUserUid(annaUUID)).willReturn(Optional.of(anna));
    given(fakeDataDao.updateUser(anna)).willReturn(1);

    ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

    int updateResult = userService.updateUser(anna);

    verify(fakeDataDao).selectUserByUserUid(annaUUID);
    verify(fakeDataDao).updateUser(captor.capture());

    User user = captor.getValue();
    assertAnnaFields(user);

    assertThat(updateResult).isEqualTo(1);

	}

	@Test
	public void shouldRemoveUser() {
    UUID annaUUID = UUID.randomUUID();
    User anna = new User(annaUUID , "Anna", "Montana", Gender.FEMALE, 30, "anna@gmail.com");

    given(fakeDataDao.selectUserByUserUid(annaUUID)).willReturn(Optional.of(anna));
    given(fakeDataDao.deleteUserByUserUid(annaUUID)).willReturn(1);


    int deleteResult = userService.removeUser(annaUUID);

    verify(fakeDataDao).selectUserByUserUid(annaUUID);
    verify(fakeDataDao).deleteUserByUserUid(annaUUID);

    assertThat(deleteResult).isEqualTo(1);
  }

	@Test
	public void shouldInsertUser() {
    User anna = new User(null , "Anna", "Montana", Gender.FEMALE, 30, "anna@gmail.com");

    given(fakeDataDao.insertUser(any(UUID.class),eq(anna))).willReturn(1);

    ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
    int insertResult = userService.insertUser(anna);
    verify(fakeDataDao).insertUser(any(UUID.class), captor.capture());
    User user = captor.getValue();
    assertAnnaFields(user);

    assertThat(insertResult).isEqualTo(1);
  }
  
  private void assertAnnaFields(User testUsr) {
    assertThat(testUsr.getAge()).isEqualTo(30);
    assertThat(testUsr.getFirstName()).isEqualTo("Anna");
    assertThat(testUsr.getLastName()).isEqualTo("Montana");
    assertThat(testUsr.getGender()).isEqualTo(Gender.FEMALE);
    assertThat(testUsr.getEmail()).isEqualTo("anna@gmail.com");
    assertThat(testUsr.getUserUid()).isNotNull();
  }
}
