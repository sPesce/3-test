package com.steve.springbootstarter.resource;

public class ErrorMessage {
  private final String errorMessage;

  public ErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  @Override
    public String toString() {
      return "{" +
        " message='" + errorMessage + "'" +
        "}";
    }
}
