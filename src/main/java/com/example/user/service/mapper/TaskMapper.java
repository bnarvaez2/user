package com.example.user.service.mapper;

import com.example.user.common.enums.StatusEnum;
import com.example.user.dto.TaskRequest;
import com.example.user.dto.TaskResponse;
import com.example.user.repository.entity.TaskEntity;
import com.example.user.repository.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper(componentModel = "spring", imports = StatusEnum.class)
public interface TaskMapper {

  TaskMapper TASK_MAPPER = Mappers.getMapper(TaskMapper.class);

  @Mapping(target = "status", expression = "java(StatusEnum.PENDIENTE)")
  @Mapping(source = "userEntity", target = "user")
  TaskEntity toEntity(TaskRequest request, UserEntity userEntity);

  List<TaskEntity> toEntities(List<TaskRequest> requestList);

  TaskResponse toResponse(TaskEntity entity);

  List<TaskResponse> toResponse(List<TaskEntity> entities);
}
