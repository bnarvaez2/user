package com.example.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserAlreadyExistException extends ResponseStatusException {
  public UserAlreadyExistException() {
    super(HttpStatus.CONFLICT, "The username is already exist");
  }
}
