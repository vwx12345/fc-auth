package com.example.fc_auth.util;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.fc_auth.model.Employee;
import com.example.fc_auth.model.EmployeeRole;
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
    EmployeeRole employeeRole1 = EmployeeRole.builder()
        .name("인사팀")
        .build();
    EmployeeRole employeeRole2 = EmployeeRole.builder()
        .name("IT팀")
        .build();

    Set<EmployeeRole> employeeRoleSet = Set.of(employeeRole1, employeeRole2);

    Employee employee = Employee.builder()
        .kakaoNickName("nickName")
        .employeeRoles(employeeRoleSet)
        .build();

    String token = JwtUtil.createToken(employee);

    List res = JwtUtil.parseToken(token).get("roles", List.class);

    assertEquals(employeeRoleSet.size(), res.size());
    assertTrue(res.contains(employeeRole1.getName()));
    assertTrue(res.contains(employeeRole1.getName()));
  }
}
