package com.example.fc_auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class ValidateTokenDto {
  private String token;

  private Long app;

  private String method;

  private String path;

}
