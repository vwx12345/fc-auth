package com.example.fc_auth.service;

import com.example.fc_auth.model.KakaoUserInfoRespDto;
import com.example.fc_auth.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

  private final KakaoService kakaoService;

  public ResponseEntity login(String code) {
    String accessToken = kakaoService.getAccessToken(code);
    return new ResponseEntity(accessToken, HttpStatus.OK);
  }

  public ResponseEntity getKakaoUser(String accessToken){
    KakaoUserInfoRespDto dto = kakaoService.getUserInfo(accessToken);
    String kakaoNickName = dto.getKakaoAccount().getProfile().getNickName();
    kakaoService.checkIsRegistered(kakaoNickName);

    return new ResponseEntity("환영합니다 "+kakaoNickName +"님", HttpStatus.OK);
  }
}
