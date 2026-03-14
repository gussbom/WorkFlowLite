package com.gusbomcode.workflowlite.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI workflowLiteOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("WorkflowLite API")
                        .description("Backend API for WorkFlowLite management")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Augustine E. Onaodowan")
                                .email("ae.onaodowan@gmail.com")));
    }
}
