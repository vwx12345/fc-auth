package com.example.fc_auth.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true) //모르는 Json이 들어오면 무시
public class KakaoUserInfoRespDto {

  @JsonProperty("kakao_account")
  private KakaoAccount kakaoAccount;

  @JsonProperty("id")
  private Long id;

}
