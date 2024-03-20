package com.example.user.controller;

import static com.example.user.common.util.ResponseUtil.response;
import com.example.user.dto.GenericResponse;
import com.example.user.dto.TaskRequest;
import com.example.user.dto.TaskResponse;
import com.example.user.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
  private final TaskService service;

  public TaskController(TaskService taskService) {
    this.service = taskService;
  }

  @PostMapping("/{username}")
  public ResponseEntity<GenericResponse> create(@RequestBody TaskRequest request, @PathVariable String username) {
    service.create(request, username);

    return response("Task was created.", HttpStatus.CREATED);
  }

  @GetMapping("/{username}/all")
  public List<TaskResponse> findAllByUser(@PathVariable String username) {
    return service.findAllByUser(username);
  }

  @PutMapping("/{username}/{idTask}/change-status-complete")
  public ResponseEntity<GenericResponse> updateStatus(@PathVariable String username, @PathVariable Long idTask) {
    service.updateStatus(username, idTask);

    return response("Task was complated.", HttpStatus.OK);
  }

  @DeleteMapping("/{username}/{idTask}")
  public ResponseEntity<GenericResponse> delete(@PathVariable String username, @PathVariable Long idTask) {
    service.delete(username, idTask);

    return response("Task was deleted.", HttpStatus.OK);
  }
}
