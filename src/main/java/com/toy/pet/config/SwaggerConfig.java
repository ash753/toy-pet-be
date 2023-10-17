package com.toy.pet.config;

import com.toy.pet.domain.common.Constant;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@EnableMethodSecurity
@Configuration
@OpenAPIDefinition
public class SwaggerConfig {
    @Bean
    public OpenAPI baseOpenAPi(){
        return new OpenAPI()
                .components(new Components()
                        // 여기부터 추가 부분
                        .addSecuritySchemes(HttpHeaders.AUTHORIZATION, new SecurityScheme()
                                .name(HttpHeaders.AUTHORIZATION)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")))
                .addSecurityItem(new SecurityRequirement().addList(HttpHeaders.AUTHORIZATION))
                .info(new Info().title("모두의 멍냥이"));
    }
}
