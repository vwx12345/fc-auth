package com.example.fc_auth.controller;

import com.example.fc_auth.model.Department;
import com.example.fc_auth.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Department", description = "Department API")
public class DepartmentController {
  private final DepartmentService departmentService;

  @Operation(description = "전사 부서 조회")
  @GetMapping("/departments")
  public ResponseEntity<List<Department>> listDepartments() {
    return new ResponseEntity<>(departmentService.listDepartments(), HttpStatus.OK);
  }
}
