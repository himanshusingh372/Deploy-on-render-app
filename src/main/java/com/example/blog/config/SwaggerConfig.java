package com.example.blog.config;

import java.util.Arrays;
import java.util.List;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import io.jsonwebtoken.lang.Collections;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

	public static final String AUTHORIZATION_HEADER="Authorization";
	private ApiKey apiKey() {
		return new ApiKey("JWT",AUTHORIZATION_HEADER,"header");
	}
	
	private List<SecurityContext> securityContexts(){
		return Arrays.asList(SecurityContext.builder().securityReferences(sf()).build());
	}
	
	

	private List<SecurityReference> sf(){
		AuthorizationScope scopes=new AuthorizationScope("global", "accessEverything");
		return Arrays.asList(new SecurityReference("JWT",new AuthorizationScope[] {scopes}));
	}
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(getInfo())
				.securityContexts(securityContexts())
				.securitySchemes(Arrays.asList(apiKey()))
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build();
	}

	@SuppressWarnings("unchecked")
	private ApiInfo getInfo() {
	
		return new ApiInfo("Blogging Backend Application", "this project is developed  by Himanshu Singh"
				, "1.0", "erms of Service", 
				new Contact("Himanshu singh","https://github.com/himanshusingh372","Himanshu372singh2gmail.com"), 
				"Licence of api", "api lisence of url",Collections.arrayToList(null));
	}
}
