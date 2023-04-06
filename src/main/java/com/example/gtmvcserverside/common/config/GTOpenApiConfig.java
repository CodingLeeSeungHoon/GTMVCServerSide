package com.example.gtmvcserverside.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger 기본 설정을 위한 설정 클래스입니다.
 */
@Configuration
public class GTOpenApiConfig {

    @Bean
    public OpenAPI openAPI() {

        Info info = new Info()
                .version("v1.0.0")
                .title("GT(God Tong) API")
                .description("God Tong (갓통) 팬과 스타의 자유로운 만남");

        return new OpenAPI()
                .info(info);
    }
}
