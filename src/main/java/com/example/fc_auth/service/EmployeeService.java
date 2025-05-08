package com.example.fc_auth.service;

import com.example.fc_auth.model.Employee;
import com.example.fc_auth.repository.EmployeeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeService {

  private final EmployeeRepository employeeRepository;

  public List<Employee> getAllEmployees() {
    return employeeRepository.findAll();
  }
}
