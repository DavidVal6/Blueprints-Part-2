package edu.eci.arsw.blueprintsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
@ComponentScan(basePackages = {"edu.eci.arsw.blueprints"})
@Component
public class BlueprintsAPIApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlueprintsAPIApplication.class, args);
	}
}
