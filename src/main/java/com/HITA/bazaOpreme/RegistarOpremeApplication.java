package com.HITA.bazaOpreme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@ComponentScan(basePackages = "com.HITA.bazaOpreme")
@EnableWebMvc
@EnableJpaRepositories
public class RegistarOpremeApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegistarOpremeApplication.class, args);
		System.out.println("Startup successful!");
		System.out.println("Go to :");
		System.out.println("http://localhost:8080/");
	}

}
