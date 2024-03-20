package com.example.user.service;

import com.example.user.common.enums.StatusEnum;
import com.example.user.dto.TaskRequest;
import com.example.user.dto.TaskResponse;
import com.example.user.exception.TaskConflictException;
import com.example.user.exception.TaskNotFoundException;
import com.example.user.exception.UserNotFoundException;
import com.example.user.repository.TaskRepository;
import com.example.user.repository.UserRepository;
import com.example.user.repository.entity.TaskEntity;
import com.example.user.repository.entity.UserEntity;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

  @Mock
  private TaskRepository taskRepository;

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private TaskService taskService;

  @Test
  void testCreateTask_Success() {
    TaskRequest taskRequest = new TaskRequest();
    taskRequest.setTitle("Task Title");
    taskRequest.setDescription("Task Description");

    UserEntity userEntity = new UserEntity();
    userEntity.setUsername("testUser");

    when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(userEntity));

    assertDoesNotThrow(() -> taskService.create(taskRequest, "testUser"));

    verify(taskRepository, times(1)).save(any());
  }

  @Test
  void testCreateTask_UserNotFound() {
    TaskRequest taskRequest = new TaskRequest();
    taskRequest.setTitle("Task Title");
    taskRequest.setDescription("Task Description");

    when(userRepository.findByUsername("nonExistentUser")).thenReturn(Optional.empty());

    assertThrows(UserNotFoundException.class, () -> taskService.create(taskRequest, "nonExistentUser"));
  }

  @Test
  void testFindAllByUser_Success() {
    UserEntity userEntity = new UserEntity();
    userEntity.setUsername("testUser");

    TaskEntity taskEntity = new TaskEntity();
    taskEntity.setTitle("Task Title");
    taskEntity.setDescription("Task Description");
    taskEntity.setUser(userEntity);

    when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(userEntity));
    when(taskRepository.findAllByUser(userEntity)).thenReturn(singletonList(taskEntity));

    List<TaskResponse> result = taskService.findAllByUser("testUser");

    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("Task Title", result.get(0).getTitle());
    assertEquals("Task Description", result.get(0).getDescription());
  }

  @Test
  void testFindAllByUser_UserNotFound() {
    when(userRepository.findByUsername("nonExistentUser")).thenReturn(Optional.empty());

    assertThrows(UserNotFoundException.class, () -> taskService.findAllByUser("nonExistentUser"));
  }

  @Test
  void testUpdateStatus_Success() {
    UserEntity userEntity = new UserEntity();
    userEntity.setId(1L);
    userEntity.setUsername("testUser");

    TaskEntity taskEntity = new TaskEntity();
    taskEntity.setId(1L);
    taskEntity.setStatus(StatusEnum.PENDIENTE);
    taskEntity.setUser(userEntity);

    when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(userEntity));
    when(taskRepository.findById(1L)).thenReturn(Optional.of(taskEntity));

    assertDoesNotThrow(() -> taskService.updateStatus("testUser", 1L));

    assertEquals(StatusEnum.COMPLETADO, taskEntity.getStatus());
    verify(taskRepository, times(1)).save(taskEntity);
  }

  @Test
  void testUpdateStatus_TaskNotFound() {
    when(taskRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(TaskNotFoundException.class, () -> taskService.updateStatus("testUser", 1L));
  }

  @Test
  void testUpdateStatus_UserConflict() {
    UserEntity userEntity = new UserEntity();
    userEntity.setId(2L);
    userEntity.setUsername("otherUser");

    UserEntity userEntityIncorrect = new UserEntity();
    userEntityIncorrect.setId(3L);
    userEntityIncorrect.setUsername("otherUser");

    TaskEntity taskEntity = new TaskEntity();
    taskEntity.setId(1L);
    taskEntity.setStatus(StatusEnum.PENDIENTE);
    taskEntity.setUser(userEntity);

    when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(userEntityIncorrect));
    when(taskRepository.findById(1L)).thenReturn(Optional.of(taskEntity));

    assertThrows(TaskConflictException.class, () -> taskService.updateStatus("testUser", 1L));
  }

  @Test
  void testDeleteTask_Success() {
    UserEntity userEntity = new UserEntity();
    userEntity.setId(1L);
    userEntity.setUsername("testUser");

    TaskEntity taskEntity = new TaskEntity();
    taskEntity.setId(1L);
    taskEntity.setUser(userEntity);

    when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(userEntity));
    when(taskRepository.findById(1L)).thenReturn(Optional.of(taskEntity));

    assertDoesNotThrow(() -> taskService.delete("testUser", 1L));

    verify(taskRepository, times(1)).delete(taskEntity);
  }

  @Test
  void testDeleteTask_TaskNotFound() {
    when(taskRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(TaskNotFoundException.class, () -> taskService.delete("testUser", 1L));
  }

  @Test
  void testDeleteTask_UserConflict() {
    UserEntity userEntity = new UserEntity();
    userEntity.setId(2L);
    userEntity.setUsername("otherUser");

    UserEntity userEntityIncorrect = new UserEntity();
    userEntityIncorrect.setId(3L);
    userEntityIncorrect.setUsername("otherUser");

    TaskEntity taskEntity = new TaskEntity();
    taskEntity.setId(1L);
    taskEntity.setUser(userEntity);

    when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(userEntityIncorrect));
    when(taskRepository.findById(1L)).thenReturn(Optional.of(taskEntity));

    assertThrows(TaskConflictException.class, () -> taskService.delete("testUser", 1L));
  }
}
