package com.canaristar.backend;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvEntry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BackendApplication {

	public static void main(String[] args) {

        Dotenv dotenv = Dotenv.load();

        for (DotenvEntry entry : dotenv.entries()) {
            String key = entry.getKey();
            String value = entry.getValue();

            System.setProperty(key, value);
        }

        SpringApplication.run(BackendApplication.class, args);
    }
}
