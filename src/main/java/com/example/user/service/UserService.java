package com.example.user.service;

import com.example.user.dto.UserRequest;
import com.example.user.dto.UserResponse;
import com.example.user.exception.UserAlreadyExistException;
import com.example.user.exception.UserNotFoundException;
import com.example.user.repository.UserRepository;
import static com.example.user.service.mapper.UserMapper.USER_MAPPER;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class UserService {

  private final UserRepository repository;

  @Autowired
  public UserService(UserRepository repository) {
    this.repository = repository;
  }

  @Transactional
  public void create(UserRequest request) {
    if(repository.existsByUsername(request.getUsername()))
      throw new UserAlreadyExistException();

    repository.save(USER_MAPPER.toEntity(request));
  }

  @Transactional(readOnly = true)
  public List<UserResponse> findAll() {
    return USER_MAPPER.toResponse(repository.findAll());
  }

  @Transactional(readOnly = true)
  public UserResponse findByUsername(String username) {
    val entity = repository.findByUsername(username);

    if(entity.isEmpty()) throw new UserNotFoundException();

    return USER_MAPPER.toResponse(entity.get());
  }
}
