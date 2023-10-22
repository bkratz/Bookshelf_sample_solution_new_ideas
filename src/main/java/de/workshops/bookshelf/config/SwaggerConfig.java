package de.workshops.bookshelf.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class SwaggerConfig {

    @Bean
    public OpenAPI api(BookshelfProperties bookshelfProperties) {
        return new OpenAPI()
                .info(
                        new Info()
                                .title(bookshelfProperties.getTitle())
                                .version(bookshelfProperties.getVersion())
                                .description(bookshelfProperties.getDescription() + bookshelfProperties.getCapacity())
                                .license(new License()
                                                .name(bookshelfProperties.getLicense().getName())
                                                .url(bookshelfProperties.getLicense().getUrl().toString())
                                )
                );
    }
}
