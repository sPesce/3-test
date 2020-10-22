package com.steve.springbootstarter.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;

import com.google.common.collect.ImmutableList;
import com.steve.springbootstarter.dao.FakeDataDao;
import com.steve.springbootstarter.model.User;
import com.steve.springbootstarter.model.User.Gender;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


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

    ImmutableList
    
    given(fakeDataDao.selectAllUsers()).willReturn();
		List<User> actualValue = userService.getAllUsers();

    assertThat(actualValue).hasSize(1);
	}

	@Test
	public void shouldGetUser() {
		// TODO: initialize args
		UUID userUid;

		Optional<User> actualValue = userService.getUser(userUid);

		// TODO: assert scenario
	}

	@Test
	public void shouldUpdateUser() {
		// TODO: initialize args
		User user;

		int actualValue = userService.updateUser(user);

		// TODO: assert scenario
	}

	@Test
	public void shouldRemoveUser() {
		// TODO: initialize args
		UUID userUid;

		int actualValue = userService.removeUser(userUid);

		// TODO: assert scenario
	}

	@Test
	public void shouldInsertUser() {
		// TODO: initialize args
		UUID userUid;
		User user;

		int actualValue = userService.insertUser(userUid, user);

		// TODO: assert scenario
	}
}
