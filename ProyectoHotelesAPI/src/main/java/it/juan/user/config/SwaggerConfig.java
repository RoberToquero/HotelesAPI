package it.juan.user.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("Hoteles API")
                        .description("Proyecto API")
                        .contact(new Contact()
                                .name("Roberto")
                                .email("roberto")
                                .url("https://roberto.com"))
                        .version("1.0"));
    }

}