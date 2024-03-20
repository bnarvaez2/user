package com.example.user.dto;

import com.example.user.common.enums.StatusEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskResponse {

  private Long id;
  private String title;
  private String description;
  private StatusEnum status;
}
