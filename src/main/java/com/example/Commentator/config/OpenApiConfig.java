package com.example.Commentator.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Article Comments API",
                version = "1.0",
                description = "API for posting, replying, liking, editing, and deleting comments on articles."
        )
)
public class OpenApiConfig { }
