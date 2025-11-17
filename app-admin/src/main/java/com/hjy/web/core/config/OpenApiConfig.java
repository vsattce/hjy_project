package com.hjy.web.core.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("hjy个人学习项目")
                .description("这是一个个人学习项目，基于springboot3.4.11")
                .version("0.0.0")
                .contact(new Contact()
                        .name("hjy")
                        .email("584153255@qq.com")
                        .url("https://www.hjy.cn"))
        );
    }
}
