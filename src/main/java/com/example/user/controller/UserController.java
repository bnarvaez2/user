package com.example.user.controller;
import static com.example.user.common.util.ResponseUtil.response;
import com.example.user.dto.GenericResponse;
import com.example.user.dto.UserRequest;
import com.example.user.dto.UserResponse;
import com.example.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping
  public ResponseEntity<GenericResponse> createUser(@Valid @RequestBody UserRequest request) {
    userService.create(request);

    return response("User was created.", HttpStatus.CREATED);
  }

  @GetMapping
  public List<UserResponse> getAllUsers() {
    return userService.findAll();
  }

  @GetMapping("/{username}")
  public UserResponse getUserByUsername(@PathVariable String username) {
    return userService.findByUsername(username);
  }
}

