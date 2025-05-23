package com.example.fc_auth.controller;

import com.example.fc_auth.model.Employee;
import com.example.fc_auth.service.EmployeeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Department", description = "Department API")
public class EmployeeController {

  private final EmployeeService employeeService;

  @GetMapping(value = "/employees",
  produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Employee>> listAll() {
    return new ResponseEntity<>(employeeService.getAllEmployees(), HttpStatus.OK);
  }

  @PostMapping(value = "/admin/employees",  produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Employee> createEmployee(@RequestParam String firstName,
      @RequestParam String lastName, @RequestParam Long departmentId, @RequestParam String kakaoNickName) {
    return new ResponseEntity<>(employeeService.createEmployee(firstName, lastName, departmentId, kakaoNickName), HttpStatus.CREATED);
  }
}
