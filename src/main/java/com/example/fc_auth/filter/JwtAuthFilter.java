package com.example.fc_auth.filter;

import com.example.fc_auth.model.Employee;
import com.example.fc_auth.repository.EmployeeRepository;
import com.example.fc_auth.service.KakaoService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
    if (StringUtils.isNotBlank(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) { // "Authorization: Bearer token_value" 양식이어야함
      String accessToken = authorizationHeader.substring(7);

      String nickName = kakaoService.getUserInfo(accessToken).getKakaoAccount().getProfile().getNickName();
      kakaoService.checkIsRegistered(nickName);

      if(!employeeRepository.existsByKakaoNickName(nickName)) throw new IllegalArgumentException("존재하지 않는 회원입니다.");

      Employee employee = employeeRepository.findByKakaoNickName(nickName);

      List<GrantedAuthority> authorities = new ArrayList<>();
      authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
      if(Employee.isHR(employee)){
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
      }
      Authentication authentication = new TestingAuthenticationToken(employee.getFirstName(), "password"
          , authorities);

      /** 설명용
      // principal은 사용자 식별용, credential은 인증을 위한 정보(비밀번호,토큰), authorities는 원하는 걸 넣을 수 있음(role 등등)
      Authentication authentication = new TestingAuthenticationToken(employee.getFirstName(), "password"
          , "ROLE_USER"); // 왼쪽과 같은 형태지만 Config에서 왼쪽처럼 사용(ROLE_ prefix 사용) .requestMatchers("/departments/**").hasRole("USER")
                                     // Role 말고도 다른 authorities가 있기 때문에 구분하기 위해서
       */

      // context에 넣는 것
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    // filter는 체인 방식, doFilter 안하면 체인이 끊김
    filterChain.doFilter(request, response);
  }
}
