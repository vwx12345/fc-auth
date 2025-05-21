package com.example.fc_auth.service;

import com.example.fc_auth.model.Employee;
import com.example.fc_auth.repository.EmployeeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService {

  private final EmployeeRepository employeeRepository;

  public List<Employee> getAllEmployees() {
    String name = SecurityContextHolder.getContext().getAuthentication().getName();
    log.info("name:" + name);
    return employeeRepository.findAll();
  }

  public Employee createEmployee(String firstName, String lastName, Long departmentId, String kakaoNickName) {

    Employee employee = Employee.builder()
        .firstName(firstName)
        .lastName(lastName)
        .departmentId(departmentId)
        .kakaoNickName(kakaoNickName)
        .build();
    return employeeRepository.save(employee);
  }
}
