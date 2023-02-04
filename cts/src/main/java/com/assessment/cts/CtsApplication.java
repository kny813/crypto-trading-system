package com.assessment.cts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.assessment.cts.*")
@ComponentScan("com.assessment.cts.*")
@EntityScan("com.assessment.cts.*")
@SpringBootApplication
@Configuration
public class CtsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CtsApplication.class, args);
	}

}
