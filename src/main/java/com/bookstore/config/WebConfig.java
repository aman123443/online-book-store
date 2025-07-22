package com.bookstore.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String uploadDir = new File(System.getProperty("user.dir"), "uploads").getAbsolutePath();
        File directory = new File(uploadDir);
        if (!directory.exists() && !isTestEnvironment()) {
            directory.mkdirs();
        }
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadDir + "/")
                .setCachePeriod(3600);
    }

    private boolean isTestEnvironment() {
        return System.getProperty("spring.profiles.active") != null &&
               System.getProperty("spring.profiles.active").contains("test");
    }
}