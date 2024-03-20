package com.example.user.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class UserResponse {
  private String username;
  private List<TaskResponse> tasks;
}
