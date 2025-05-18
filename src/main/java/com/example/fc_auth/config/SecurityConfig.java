package com.example.fc_auth.config;

import com.example.fc_auth.filter.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity //spring security의 bean임을 알려주는 것
public class SecurityConfig {
  private static final String[] ALLOWLIST = {
      "/v3/**",
      "/swagger-ui/**",
  };

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable); // csrf 보안을 해제
    http.cors(Customizer.withDefaults()); // cors 설정

    http.sessionManagement(sessionManagement-> sessionManagement.sessionCreationPolicy(
        SessionCreationPolicy.STATELESS));
    // 위에까지가 REST API를 위한 설정

    http.formLogin(AbstractHttpConfigurer::disable); // formLogin 비활성화(user password 사용 안하기 위해서)

    http.addFilterBefore(new JwtAuthFilter(), UsernamePasswordAuthenticationFilter.class); // filter가 적용될 위치

    http.authorizeHttpRequests(authorize
        -> authorize
        .requestMatchers(ALLOWLIST).permitAll() // ALLOWLIST에 있는 값들은 허용
        .anyRequest().authenticated()); // 모든 요청에 적용되게 설정

    return http.build();
  }
}
