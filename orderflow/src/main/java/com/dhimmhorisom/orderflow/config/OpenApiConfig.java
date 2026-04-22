package com.dhimmhorisom.orderflow.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("OrderFlow API")
                        .version("1.0")
                        .description("API REST para gerenciamento de usuários, categorias, produtos e pedidos com autenticação JWT.")
                        .contact(new Contact()
                                .name("Dhim")
                                .email("dhimmhorisom.silva@gmail.com"))
                        .license(new License()
                                .name("Uso acadêmico / portfólio")));
    }
}