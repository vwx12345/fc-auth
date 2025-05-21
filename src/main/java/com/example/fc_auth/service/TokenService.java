package com.example.fc_auth.service;

import com.example.fc_auth.dto.AppTokenRespDto;
import com.example.fc_auth.dto.ValidateTokenDto;
import com.example.fc_auth.model.Api;
import com.example.fc_auth.model.App;
import com.example.fc_auth.repository.ApiRepository;
import com.example.fc_auth.repository.AppRepository;
import com.example.fc_auth.util.JwtUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {

  private final AppRepository appRepository;
  private final ApiRepository apiRepository;

  public AppTokenRespDto createToken(Long appId){
    App app = appRepository.findById(appId).orElseThrow(() -> new RuntimeException("App not found"));
    return AppTokenRespDto.builder()
        .token(JwtUtil.createAppToken(app))
        .build();
  }

  public ResponseEntity<String> validateToken(ValidateTokenDto dto) {
    log.info(dto.getPath());
    Api api = apiRepository.findByPathAndMethod(dto.getPath(), dto.getMethod());
    return JwtUtil.validateToken(dto, api);
  }
}
