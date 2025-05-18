package com.example.fc_auth.util;

import com.example.fc_auth.model.Employee;
import com.example.fc_auth.model.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class JwtUtil {
  private final static Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
  private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 24시간

  public static String createToken(Employee employee) {
    Date now = new Date();
    Date expireAt = new Date(now.getTime() + EXPIRATION_TIME);

    Map<String, Object> claims = new HashMap<>();
    claims.put("nickname", employee.getKakaoNickName());
    if(employee.getRoles() != null && !employee.getRoles().isEmpty()){
      claims.put("roles",  employee.getRoles().stream().map(Role::getName).collect(Collectors.toSet()));
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

  public static Claims parseToken(String token){
    return Jwts.parser()
        .setSigningKey(SECRET_KEY)
        .build()
        .parseClaimsJws(token)
        .getBody();
  }
}
