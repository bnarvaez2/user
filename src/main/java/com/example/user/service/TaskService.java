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
import static com.example.user.service.mapper.TaskMapper.TASK_MAPPER;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class TaskService {

  private final TaskRepository repository;
  private final UserRepository userRepository;

  @Autowired
  public TaskService(TaskRepository repository, UserRepository userRepository) {
    this.repository = repository;
    this.userRepository = userRepository;
  }

  @Transactional
  public void create(TaskRequest request, String username) {
    repository.save(TASK_MAPPER.toEntity(request, getUserByUsername(username)));
  }

  @Transactional(readOnly = true)
  public List<TaskResponse> findAllByUser(String username) {
    val list = repository.findAllByUser(getUserByUsername(username));

    return TASK_MAPPER.toResponse(list);
  }

  @Transactional
  public void updateStatus(String username, Long idTask) {
    val task = getTask(username, idTask);
    task.setStatus(StatusEnum.COMPLETADO);
    repository.save(task);
  }

  @Transactional
  public void delete(String username, Long idTask) {
    val task = getTask(username, idTask);
    repository.delete(task);
  }

  private TaskEntity getTask(String username, Long idTask) {
    val optionalTask = repository.findById(idTask);

    if (optionalTask.isEmpty())  throw new TaskNotFoundException();

    val user = getUserByUsername(username);
    val task = optionalTask.get();

    if (!user.getId().equals(task.getUser().getId())) throw new TaskConflictException();

    return optionalTask.get();
  }

  private UserEntity getUserByUsername(String username) {
    val user = userRepository.findByUsername(username);

    if (user.isEmpty()) throw new UserNotFoundException();

    return user.get();
  }
}
