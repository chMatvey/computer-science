package com.github.chudakov.app;

import com.github.chudakov.app.model.Worker;
import com.github.chudakov.app.repository.WorkerRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
		WorkerRepository workerRepository = context.getBean(WorkerRepository.class);
		List<Worker> workers = workerRepository.findByNumberBetween(20, 50);
		for (Worker worker : workers) {
			System.out.println(worker);
		}
	}

}
