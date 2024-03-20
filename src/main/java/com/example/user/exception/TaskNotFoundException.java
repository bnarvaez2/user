package com.example.user.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import org.springframework.web.server.ResponseStatusException;

public class TaskNotFoundException extends ResponseStatusException {
  public TaskNotFoundException() {
    super(NOT_FOUND, "Task not found.");
  }
}
