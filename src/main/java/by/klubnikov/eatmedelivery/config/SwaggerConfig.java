package by.klubnikov.eatmedelivery.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(name = "JWT", scheme = "bearer", type= SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class SwaggerConfig {

    @Bean
    public OpenAPI getOpenAPIDefinition(){
        return new OpenAPI().info(new Info()
                .summary("Delivery service REST project")
                .description("REST service which provides you opportunity to get " +
                        "every dish you from restaurants of your city!!!")
                .title("Eat Me Delivery")
//                .license(new License().name("Protected by mega super protection"))
                .version("1.0.0")
                .contact(new Contact()
                        .name("Alex Klubnikov")
                        .email("aklubnikov.java@gmail.com")));
    }

    @Bean
    public GroupedOpenApi getFirstControllerAPI(){
        return GroupedOpenApi.builder()
                .group("Authentication")
                .displayName("Authentication controller group")
                .pathsToMatch("/auth/**")
                .build();
    }
    @Bean
    public GroupedOpenApi getSecondControllerAPI(){
        return GroupedOpenApi.builder()
                .group("Restaurants")
                .displayName("Restaurants controller group")
                .pathsToMatch("/restaurants/**")
                .build();
    }
    @Bean
    public GroupedOpenApi getThirdControllerAPI(){
        return GroupedOpenApi.builder()
                .group("Restaurants-manager")
                .displayName("Restaurants-manager controller group")
                .pathsToMatch("/restaurants-manager/**")
                .build();
    }

}
