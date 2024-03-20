package com.example.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class TaskConflictException extends ResponseStatusException {
  public TaskConflictException() {
    super(HttpStatus.CONFLICT, "The username does not correspond to the task user.");
  }
}
