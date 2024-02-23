package com.HITA.bazaOpreme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.HITA.bazaOpreme")

public class BazaOpremeApplication {

	public static void main(String[] args) {
		SpringApplication.run(BazaOpremeApplication.class, args);
		System.out.println("Startup successful!");
		System.out.println("Go to :");
		System.out.println("http://localhost:8080/");
	}

}
