package com.example.fc_auth.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI openAPI(){
    return new OpenAPI()
        .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication")) // api마다 적용
        .components(new Components().addSecuritySchemes("Bearer Authentication", createAPIKeyScheme())) // 토큰 적용하는 버튼
        .info(apiInfo());
  }

  private Info apiInfo(){
    return new Info()
        .title("FC Auth API")
        .description("FC Auth API")
        .version("1.0.0");
  }

  // swagger에 헤더 추가를 위해서
  private SecurityScheme createAPIKeyScheme(){
    return new SecurityScheme()
        .type(SecurityScheme.Type.HTTP)
        .bearerFormat("JWT")
        .scheme("bearer");
  }

}
