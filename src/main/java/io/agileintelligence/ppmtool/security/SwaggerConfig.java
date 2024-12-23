package io.agileintelligence.ppmtool.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("io.agileintelligence.ppmtool"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfoMetaData());
    }

    private ApiInfo apiInfoMetaData() {
        return new ApiInfoBuilder()
                .title("API Documentation")
                .description("API Endpoints")
                .contact(new Contact("Dev-Team", "https://www.dev-team.com/", "dev-team@example.com"))
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .version("1.0.0")
                .build();
    }
}