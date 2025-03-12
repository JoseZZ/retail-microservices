package com.jgonzal.retail.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${server.servlet.context-path:/}")
    private String contextPath;

    @Value("${server.port:8080}")
    private String serverPort;

    @Bean
    public OpenAPI myOpenAPI() {
        Server localServer = new Server()
                .url("http://localhost:" + serverPort + contextPath)
                .description("Servidor Local");

        Server dockerServer = new Server()
                .url("http://customer-service:" + serverPort + contextPath)
                .description("Servidor Docker");

        Contact contact = new Contact()
                .name("Retail API")
                .email("support@retail.com");

        Info info = new Info()
                .title("API de Gestión de Clientes")
                .version("1.0")
                .contact(contact)
                .description("Esta API expone endpoints para gestionar clientes.")
                .license(new License().name("Apache 2.0").url("http://springdoc.org"));

        return new OpenAPI()
                .info(info)
                .servers(List.of(localServer, dockerServer));
    }
}