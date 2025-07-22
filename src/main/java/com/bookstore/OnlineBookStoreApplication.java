package com.bookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.context.annotation.Bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
public class OnlineBookStoreApplication {

    private static final Logger logger = LoggerFactory.getLogger(OnlineBookStoreApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(OnlineBookStoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner logPort(ServletWebServerApplicationContext webServerAppContext) {
        return args -> {
            int port = webServerAppContext.getWebServer().getPort();
            logger.info("Application started on port: {}", port);
        };
    }
}