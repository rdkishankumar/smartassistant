package com.inchbyinch.smartassistant.config;

import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
@Configuration

public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {

        Parameter authorization = new Parameter()
                .in("header")
                .name("Authorization")
                .required(false)
                .description("Auth Token")
                .schema(new StringSchema());

        Parameter clientName = new Parameter()
                .in("header")
                .name("Client-Name")
                .required(false)
                .description("Client name")
                .schema(new StringSchema());

        Parameter clientId = new Parameter()
                .in("header")
                .name("Client-Id")
                .required(false)
                .description("Client Id")
                .schema(new StringSchema());

        Parameter mobile = new Parameter()
                .in("header")
                .name("Mobile")
                .required(false)
                .description("Mobile number")
                .schema(new StringSchema());

        Parameter userId = new Parameter()
                .in("header")
                .name("User-Id")
                .required(false)
                .description("User Id")
                .schema(new StringSchema());

        return new OpenAPI()
                .info(new Info()
                        .title("OpenAI Service API")
                        .version("1.0")
                        .description("API documentation"))
                .components(new Components()
                        .addParameters("Authorization", authorization)
                        .addParameters("Client-Name", clientName)
                        .addParameters("Client-Id", clientId)
                        .addParameters("Mobile", mobile)
                        .addParameters("User-Id", userId));
    }
}
