package com.example.fc_auth.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.Getter;

@Entity
@Getter
public class App {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(example = "123", description = "auto increment pk")
  private Long id;

  @Schema(example = "Calendar", description = "시스템 이름")
  private String name;

  @OneToMany
  @JoinColumn(name = "app_id")
  private List<Api> apis;
}
