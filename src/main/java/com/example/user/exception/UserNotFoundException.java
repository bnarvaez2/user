package com.example.user.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import org.springframework.web.server.ResponseStatusException;

public class UserNotFoundException extends ResponseStatusException {
  public UserNotFoundException() {
    super(NOT_FOUND, "User not found.");
  }
}
