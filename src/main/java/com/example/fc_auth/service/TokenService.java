package com.example.fc_auth.service;

import com.example.fc_auth.dto.AppTokenRespDto;
import com.example.fc_auth.model.App;
import com.example.fc_auth.repository.AppRepository;
import com.example.fc_auth.util.JwtUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

  private final AppRepository appRepository;

  public AppTokenRespDto createToken(Long appId){
    App app = appRepository.findById(appId).orElseThrow(() -> new RuntimeException("App not found"));
    return AppTokenRespDto.builder()
        .token(JwtUtil.createAppToken(app))
        .build();
  }
}
