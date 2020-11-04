package com.steve.springbootstarter.model;

import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

  //user id
  private final UUID userUid;
  
  private final String firstName;
  private final String lastName;
  private final Gender gender;
  private final Integer age;
  private final String email;

  

  public User( 
  @JsonProperty("userUid") UUID userUid,
  @JsonProperty("firstName") String firstName,
  @JsonProperty("lastName") String lastName,
  @JsonProperty("gender") Gender gender,
  @JsonProperty("age") Integer age,
  @JsonProperty("email") String email) {
    this.userUid = userUid;
    this.firstName = firstName;
    this.lastName = lastName;
    this.gender = gender;
    this.age = age;
    this.email = email;
  }

  public static User newUser(UUID userUid, User user) {
    return new User (
      userUid,
      user.getFirstName(),
      user.getLastName(),
      user.getGender(),
      user.getAge(),
      user.getEmail()
      );
  }

  @JsonProperty("id")
  public UUID getUserUid() { return userUid;}

  public String getFirstName() { return this.firstName;}

  public String getLastName() { return this.lastName;}

  public Gender getGender() { return this.gender;}

  public Integer getAge() { return this.age;}

  public String getEmail() { return this.email;}

  public String getFullName() {
    return firstName + " " + lastName;
  }

  public LocalDate getDateOfBirth() {
    return LocalDate.now().minusYears(age);
  }

  @Override
  public String toString() {
    return "{" +
      " userUid='" + getUserUid() + "'" +
      ", firstName='" + getFirstName() + "'" +
      ", lastName='" + getLastName() + "'" +
      ", gender='" + getGender() + "'" +
      ", age='" + getAge() + "'" +
      ", email='" + getEmail() + "'" +
      "}";
  }

  public enum Gender {
    MALE,
    FEMALE
  }
  
}
