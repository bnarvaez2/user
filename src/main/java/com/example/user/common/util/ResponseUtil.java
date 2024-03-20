package com.example.user.common.util;

import com.example.user.dto.GenericResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@NoArgsConstructor(access= AccessLevel.PRIVATE)
public final class ResponseUtil {
  public static ResponseEntity<GenericResponse> response(String title, String message, HttpStatus httpStatus) {
    return ResponseEntity.status(httpStatus).body(GenericResponse.builder().title(title).result(message).build());
  }

  public static ResponseEntity<GenericResponse> response(String message, HttpStatus httpStatus) {
    return ResponseEntity.status(httpStatus).body(GenericResponse.builder().result(message).build());
  }
}
