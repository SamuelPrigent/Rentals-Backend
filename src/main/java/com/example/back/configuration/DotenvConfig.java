package com.example.back.configuration;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class DotenvConfig {

    @Bean
    public Dotenv dotenv(ConfigurableEnvironment environment) {

        Dotenv dotenv = Dotenv.configure()
                .directory("Back")
                .load();

        Map<String, Object> envMap = new HashMap<>();
        dotenv.entries().forEach(e -> envMap.put(e.getKey(), e.getValue()));

        MapPropertySource propertySource = new MapPropertySource("dotenvProperties", envMap);
        environment.getPropertySources().addFirst(propertySource);

        return dotenv;
    }
}
