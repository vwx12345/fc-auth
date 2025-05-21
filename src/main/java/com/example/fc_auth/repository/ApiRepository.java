package com.example.fc_auth.repository;

import com.example.fc_auth.model.Api;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiRepository extends JpaRepository<Api, Long> {
  Api findByPathAndMethod(String path, String method);
}
