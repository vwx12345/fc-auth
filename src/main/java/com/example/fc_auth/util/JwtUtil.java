package com.example.fc_auth.util;

import com.example.fc_auth.dto.ValidateTokenDto;
import com.example.fc_auth.model.Api;
import com.example.fc_auth.model.App;
import com.example.fc_auth.model.AppRole;
import com.example.fc_auth.model.Employee;
import com.example.fc_auth.model.EmployeeRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apiguardian.api.API;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class JwtUtil {
  private final static Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
  private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 24시간

  public static String createUserToken(Employee employee) {
    Date now = new Date();
    Date expireAt = new Date(now.getTime() + EXPIRATION_TIME);

    Map<String, Object> claims = new HashMap<>();
    claims.put("nickname", employee.getKakaoNickName());
    if(employee.getEmployeeRoles() != null && !employee.getEmployeeRoles().isEmpty()){
      claims.put("roles",  employee.getEmployeeRoles().stream().map(EmployeeRole::getName).collect(Collectors.toSet()));
    } else{
      claims.put("roles", Collections.emptySet());
    }

    return Jwts.builder()
        .setSubject(employee.getKakaoNickName()) // 토큰 주제 (보통 사용자 식별자)
        .claims(claims)
        .setIssuedAt(now) // 토큰 발급 시간
        .setExpiration(expireAt) // 토큰 만료 시간
        .signWith(SECRET_KEY) // 서명 알고리즘 및 키
        .compact(); // 최종 JWT 문자열 반환
  }

  public static String createAppToken(App app) {
    Date now = new Date();
    Date expireAt = new Date(now.getTime() + EXPIRATION_TIME);

    Map<String, Object> claims = new HashMap<>();
    claims.put("type", "app");
    claims.put("roles", app.getAppRoles().stream().map(AppRole::getApi).map(Api::getId).collect(Collectors.toSet()));

    return Jwts.builder()
        .setSubject(app.getId().toString()) // 토큰 주제 (보통 사용자 식별자)
        .claims(claims)
        .setIssuedAt(now) // 토큰 발급 시간
        .setExpiration(expireAt) // 토큰 만료 시간
        .signWith(SECRET_KEY) // 서명 알고리즘 및 키
        .compact(); // 최종 JWT 문자열 반환
  }

  public static Claims parseToken(String token){
    return Jwts.parser()
        .setSigningKey(SECRET_KEY)
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  public static ResponseEntity<String> validateToken(ValidateTokenDto dto, Api api) {
    Claims claims;
    try {
      claims = parseToken(dto.getToken());
    } catch (Exception e) {
      return new ResponseEntity<>("토큰이 유효하지 않습니다.", HttpStatus.UNAUTHORIZED);
    }

    if(!claims.get("type", String.class).equals("app")){
      return new ResponseEntity<>("토큰이 유효하지 않습니다.", HttpStatus.UNAUTHORIZED);
    }

    if(claims.getExpiration().before(new Date())){
      return new ResponseEntity<>("토큰이 만료되었습니다.",HttpStatus.UNAUTHORIZED);
    }

    String roles = claims.get("roles").toString();

    if(roles.contains(api.getId().toString())){
      return new ResponseEntity<>("권한이 존재합니다.",HttpStatus.OK);
    } else {
        return new ResponseEntity<>("권한이 존재하지 않습니다.", HttpStatus.FORBIDDEN);
    }
  }
}
