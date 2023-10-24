package com.api.learning.ElearningBE.config;

import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.HashSet;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    HashSet<String> consumesAndProduces = new HashSet<>(List.of("application/json"));
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .forCodeGeneration(true)
                .consumes(consumesAndProduces)
                .produces(consumesAndProduces)
                .useDefaultResponseMessages(false)
                .select().apis(RequestHandlerSelectors.basePackage("com.api.learning.ElearningBE.controller"))
                .paths(PathSelectors.any())
                .build();
    }
}
