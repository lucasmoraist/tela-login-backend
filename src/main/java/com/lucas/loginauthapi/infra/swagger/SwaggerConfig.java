package com.lucas.loginauthapi.infra.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI swaggerDoc(){
        return new OpenAPI()
                .info(new Info()
                        .title("Moveit")
                        .description("API para gerenciar o cadastro e login dos usuários")
                        .summary("Essa api servirá para fazer a validação das informações passadas do frontend e enviá-las para o BD")
                        .version("V1")
                        .contact(new Contact()
                                .name("Lucas de Morais Nascimento Taguhci")
                                .email("luksmnt1101@gmail.com")
                                .url("https://lucasmoraist.github.io/Portfolio/")
                        )
                );
    }

}
