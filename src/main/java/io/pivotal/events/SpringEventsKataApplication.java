package io.pivotal.events;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@SpringBootApplication
@EnableAsync
public class SpringEventsKataApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringEventsKataApplication.class, args);
	}

	@Bean
	Executor taskExecutor() {
		return Executors.newVirtualThreadPerTaskExecutor();
	}

	@Bean
	ApplicationEventMulticaster applicationEventMulticaster(Executor taskExecutor) {
		SimpleApplicationEventMulticaster eventMulticaster = new SimpleApplicationEventMulticaster();
		eventMulticaster.setTaskExecutor(taskExecutor);
		return eventMulticaster;
	}
}
