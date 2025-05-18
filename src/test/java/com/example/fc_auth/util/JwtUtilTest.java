package com.example.fc_auth.util;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.fc_auth.model.Employee;
import com.example.fc_auth.model.Role;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

class JwtUtilTest {

  @Test
  public void test_nickname(){
    Employee employee = Employee.builder()
        .kakaoNickName("nickName")
        .build();

    String token = JwtUtil.createToken(employee);

    assertEquals("nickName", JwtUtil.parseToken(token).get("nickname"));

  }

  @Test
  public void test_roles(){
    Role role1 = Role.builder()
        .name("인사팀")
        .build();
    Role role2 = Role.builder()
        .name("IT팀")
        .build();

    Set<Role> roleSet = Set.of(role1, role2);

    Employee employee = Employee.builder()
        .kakaoNickName("nickName")
        .roles(roleSet)
        .build();

    String token = JwtUtil.createToken(employee);

    List res = JwtUtil.parseToken(token).get("roles", List.class);

    assertEquals(roleSet.size(), res.size());
    assertTrue(res.contains(role1.getName()));
    assertTrue(res.contains(role1.getName()));
  }
}
