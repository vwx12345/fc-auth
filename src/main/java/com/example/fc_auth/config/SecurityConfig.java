package com.example.fc_auth.config;

import com.example.fc_auth.filter.JwtAuthFilter;
import com.example.fc_auth.repository.EmployeeRepository;
import com.example.fc_auth.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity //spring security의 bean임을 알려주는 것
public class SecurityConfig {

  private final KakaoService kakaoService;
  private final EmployeeRepository employeeRepository;
  private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
  private final CustomAccessDeniedHandler customAccessDeniedHandler;

  private static final String[] ALLOWLIST = {
      "/v3/**",
      "/swagger-ui/**",
      "/kakao/**",
      "/images/**",
      "/app/token/**"
  };

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable); // csrf 보안을 해제
    http.cors(Customizer.withDefaults()); // cors 설정

    http.sessionManagement(sessionManagement-> sessionManagement.sessionCreationPolicy(
        SessionCreationPolicy.STATELESS));
    // 위에까지가 REST API를 위한 설정

    http.formLogin(AbstractHttpConfigurer::disable); // formLogin 비활성화(user password 사용 안하기 위해서)

    http.addFilterBefore(new JwtAuthFilter(kakaoService, employeeRepository), UsernamePasswordAuthenticationFilter.class); // filter가 적용될 위치

    http.authorizeHttpRequests(authorize
        -> authorize
        .requestMatchers("/admin/**").hasRole("ADMIN") // Admin이 Role이 있는 사람만 접근 가능
        .requestMatchers("/employees/**").hasRole("USER")
        .requestMatchers("/departments/**").hasRole("USER")
//        .requestMatchers("/apps/**").hasRole("USER")
        .requestMatchers(ALLOWLIST).permitAll() // ALLOWLIST에 있는 값들은 허용
        .anyRequest().authenticated()); // 모든 요청에 적용되게 설정

    http.exceptionHandling(exceptionHandling -> exceptionHandling
        .authenticationEntryPoint(customAuthenticationEntryPoint) // Authentication 에러일 때, 여기서 처리
        .accessDeniedHandler(customAccessDeniedHandler)); // AccessDenied 에러일 때, 여기서 처리

    return http.build();
  }
}
