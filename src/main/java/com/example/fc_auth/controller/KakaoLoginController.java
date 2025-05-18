package com.example.fc_auth.controller;

import com.example.fc_auth.model.KakaoUserInfoRespDto;
import com.example.fc_auth.service.KakaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("/kakao")
@RequiredArgsConstructor
public class KakaoLoginController {

  private final KakaoService kakaoService;

  @Value("${kakao.client_id}") private String clientId;
  @Value("${kakao.redirect_uri}") private String redirectUri;

  @GetMapping("/login")
  public String loginPage(Model model) {
    String location = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=" + clientId
        +"&redirect_uri=" + redirectUri;
    model.addAttribute("location", location);
    return "login";
  }

  @GetMapping("/callback")
  public ResponseEntity loginCallback(@RequestParam("code") String code) {
    log.info("code: {}", code);
    String accessToken = kakaoService.getAccessToken(code);
    log.info("accessToken: {}", accessToken);
    KakaoUserInfoRespDto userInfo = kakaoService.getUserInfo(accessToken);
    String kakaoNickName = userInfo.getKakaoAccount().getProfile().getNickName();
    log.info("nickname: {}", kakaoNickName);

    kakaoService.checkIsRegistered(kakaoNickName);

    return new ResponseEntity("환영합니다 "+kakaoNickName+"님", HttpStatus.OK);
  }
}
