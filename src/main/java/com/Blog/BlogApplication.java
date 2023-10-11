package com.Blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@SpringBootApplication
@EnableJpaRepositories
public class BlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

	@Bean
	 public OpenAPI customOpenApi(){
		return new OpenAPI()
		.info(new Info()
		.title("Sistema de Blog")
		.version("0.1.1")
		.description("EndPoint del Blog <br> Los EndPoint Se Modificaran una ver se Agrege SpringSecurity")
		.termsOfService("http://swagger.io/terms/")
		.license(new License().name("Apache 2.0").url("http:springdoc.org")));
	 }



}
