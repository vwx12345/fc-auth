package com.example.fc_auth.repository;

import com.example.fc_auth.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
  Boolean existsByKakaoNickName(String kakaoNickName);
  Employee findByKakaoNickName(String kakaoNickName);
}
