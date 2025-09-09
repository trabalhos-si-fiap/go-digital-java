package com.laros;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class LarosApplication {

	public static void main(String[] args) {
		SpringApplication.run(LarosApplication.class, args);
	}

}
