package com.example.user.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
public class UserRequest {

  @NotNull
  @NotEmpty
  private String username;
  private List<TaskRequest> tasks;
}
