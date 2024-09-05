package io.pivotal.events;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SpringEventsKataApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringEventsKataApplication.class, args);
	}
}
