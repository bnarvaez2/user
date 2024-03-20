package com.example.user.service;

import com.example.user.common.enums.StatusEnum;
import com.example.user.dto.TaskRequest;
import com.example.user.dto.UserRequest;
import com.example.user.dto.UserResponse;
import com.example.user.exception.UserAlreadyExistException;
import com.example.user.exception.UserNotFoundException;
import com.example.user.repository.UserRepository;
import com.example.user.repository.entity.TaskEntity;
import com.example.user.repository.entity.UserEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock
  private UserRepository repository;

  @InjectMocks
  private UserService userService;

  @Test
  void testCreateUser_Success() {
    UserRequest request = new UserRequest();
    request.setUsername("testUser");
    request.setTasks(Collections.singletonList(TaskRequest.builder().title("TEST").description("description").build()));

    when(repository.existsByUsername("testUser")).thenReturn(false);

    userService.create(request);

    verify(repository, times(1)).save(any());
  }

  @Test
  void testCreateUser_DuplicateUsername() {
    UserRequest request = new UserRequest();
    request.setUsername("existingUser");

    when(repository.existsByUsername("existingUser")).thenReturn(true);

    assertThrows(UserAlreadyExistException.class, () -> userService.create(request));
  }

  @Test
  void testFindAll_Success() {
    UserEntity userEntity = new UserEntity();
    userEntity.setUsername("testUser");

    when(repository.findAll()).thenReturn(Collections.singletonList(userEntity));

    List<UserResponse> result = userService.findAll();

    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("testUser", result.get(0).getUsername());
  }

  @Test
  void testFindById_Success() {
    UserEntity userEntity = new UserEntity();
    userEntity.setUsername("testUser");
    userEntity.setTask(Collections.singletonList(TaskEntity.builder().title("TEST").description("description").status(StatusEnum.PENDIENTE).build()));


    when(repository.findByUsername("testUser")).thenReturn(Optional.of(userEntity));

    UserResponse result = userService.findByUsername("testUser");

    assertNotNull(result);
    assertEquals("testUser", result.getUsername());
  }

  @Test
  void testFindById_UserNotFound() {
    when(repository.findByUsername("nonExistentUser")).thenReturn(Optional.empty());

    assertThrows(UserNotFoundException.class, () -> userService.findByUsername("nonExistentUser"));
  }
}
