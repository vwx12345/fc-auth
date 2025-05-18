package com.example.fc_auth.service;

import com.example.fc_auth.model.Employee;
import com.example.fc_auth.repository.EmployeeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@RequiredArgsConstructor
public class EmployeeService {

  private final EmployeeRepository employeeRepository;

  public List<Employee> getAllEmployees() {
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
