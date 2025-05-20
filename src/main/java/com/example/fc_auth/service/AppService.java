package com.example.fc_auth.service;

import com.example.fc_auth.model.App;
import com.example.fc_auth.repository.AppRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppService {

  private final AppRepository appRepository;

  public List<App> listApps() {
    return appRepository.findAll();
  }
}
