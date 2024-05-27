package com.example.retry_recover;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableRetry
@EnableScheduling
public class FailedRetryDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(FailedRetryDemoApplication.class, args);
	}

}
