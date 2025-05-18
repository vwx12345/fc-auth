package com.example.fc_auth.filter;

import com.example.fc_auth.model.Employee;
import com.example.fc_auth.repository.EmployeeRepository;
import com.example.fc_auth.service.KakaoService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
//OncePerRequestFilter -> 사용자 요청이 들어올 때마다 한번씩 사용되는 걸 보장

  private final KakaoService kakaoService;
  private final EmployeeRepository employeeRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String authorizationHeader = request.getHeader("Authorization");
    if (StringUtils.isNotBlank(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
      String accessToken = authorizationHeader.substring(7);
      String nickName = kakaoService.getUserInfo(accessToken).getKakaoAccount().getProfile().getNickName();
      kakaoService.checkIsRegistered(nickName);

      Employee employee = employeeRepository.findByKakaoNickName(nickName);

      // principal은 사용자 식별용, credential은 인증을 위한 정보(비밀번호,토큰), authorities는 원하는 걸 넣을 수 있음(role 등등)
      Authentication authentication = new TestingAuthenticationToken(employee.getFirstName(), "password", "ROLE_TEST");
      // context에 넣는 것
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    // filter는 체인 방식, doFilter 안하면 체인이 끊김
    filterChain.doFilter(request, response);
  }
}
