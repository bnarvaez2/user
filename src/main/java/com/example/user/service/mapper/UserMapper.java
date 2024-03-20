package com.example.user.service.mapper;

import com.example.user.common.enums.StatusEnum;
import com.example.user.dto.UserRequest;
import com.example.user.dto.UserResponse;
import com.example.user.repository.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper(componentModel = "spring", imports = StatusEnum.class)
public interface UserMapper {

  UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);

  @Mapping(source = "tasks", target = "task")
  @Mapping(target = "task.id", ignore = true)
  @Mapping(target = "task.status", expression = "java(StatusEnum.PENDIENTE)")
  UserEntity toEntity(UserRequest request);

  @Mapping(source = "task", target = "tasks")
  UserResponse toResponse(UserEntity entity);

  List<UserResponse> toResponse(List<UserEntity> entities);
}
