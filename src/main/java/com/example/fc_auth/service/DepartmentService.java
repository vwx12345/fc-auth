package com.example.fc_auth.service;

import com.example.fc_auth.model.Department;
import com.example.fc_auth.repository.DepartmentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DepartmentService {

  private final DepartmentRepository departmentRepository;

  public List<Department> listDepartments() {
    return departmentRepository.findAll();
  }
}
