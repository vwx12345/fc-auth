package com.example.fc_auth.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.List;
import lombok.Getter;

@Entity
@Getter
public class AppRole {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(example = "123", description = "auto increment pk")
  private Long id;

  @OneToOne
  @JoinColumn(name = "api_id", referencedColumnName = "id")
  private Api api;

}
