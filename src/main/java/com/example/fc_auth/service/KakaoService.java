package com.example.fc_auth.service;

import com.example.fc_auth.model.KakaoTokenRespDto;
import com.example.fc_auth.model.KakaoUserInfoRespDto;
import com.example.fc_auth.repository.EmployeeRepository;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class KakaoService {

  private final EmployeeRepository employeeRepository;

  private final String KAKAO_AUTH_URL = "https://kauth.kakao.com";
  private final String KAKAO_API_URL = "https://kapi.kakao.com";

  @Value("${kakao.client_id}") private String clientId;
  @Value("${kakao.redirect_uri}") private String redirectUri;


  public String getAccessToken(String code) {
    KakaoTokenRespDto kakaoTokenRespDto
        = WebClient.create(KAKAO_AUTH_URL)
        .post()
        .uri(uriBuilder -> uriBuilder
            .scheme("https")
            .path("/oauth/token")
            .queryParam("grant_type", "authorization_code")
            .queryParam("client_id", clientId)
            .queryParam("redirect_uri", redirectUri)
            .queryParam("code", code)
            .build())
        .header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8")
        .retrieve()
        .bodyToMono(KakaoTokenRespDto.class)
        .block();

    return kakaoTokenRespDto.getAccessToken();
  }

  public KakaoUserInfoRespDto getUserInfo(String accessToken) {
    return WebClient.create(KAKAO_API_URL)
        .get()
        .uri(uriBuilder -> uriBuilder
            .scheme("https")
            .path("/v2/user/me")
//            .queryParam("property_keys", new ArrayList["kakao_account.profile"])
            .build())
        .header(HttpHeaders.AUTHORIZATION, "Bearer "+accessToken)
        .header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8")
        .retrieve()
        .bodyToMono(KakaoUserInfoRespDto.class)
        .block();
  }

  public void checkIsRegistered(String kakaoNickName){
    // Check if the employee exists
    if (!employeeRepository.existsByKakaoNickName(kakaoNickName)) {
      throw new IllegalArgumentException("등록된 직원이 아닙니다.");
    }
  }
}
