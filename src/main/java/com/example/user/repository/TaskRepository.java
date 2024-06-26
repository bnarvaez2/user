package com.example.user.repository;

import com.example.user.repository.entity.TaskEntity;
import com.example.user.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

  List<TaskEntity> findAllByUser(UserEntity username);
}
