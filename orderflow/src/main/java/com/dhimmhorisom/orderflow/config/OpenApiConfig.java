package com.dhimmhorisom.orderflow.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                .info(new Info()
                        .title("OrderFlow API")
                        .version("1.0.0")
                        .description("""
                                API REST para gerenciamento de usuários, categorias, produtos e pedidos.
                                
                                Funcionalidades principais:
                                - Autenticação com JWT
                                - Controle de acesso por roles
                                - CRUD de categorias e produtos
                                - Criação de pedidos
                                - Controle automático de estoque
                                - Cancelamento com reversão de estoque
                                """)
                        .contact(new Contact()
                                .name("Dhim Mhorisom")
                                .email("dhimmhorisom.silva@gmail.com"))
                        .license(new License()
                                .name("Projeto de portfólio")))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name("Authorization")
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        ));
    }
}