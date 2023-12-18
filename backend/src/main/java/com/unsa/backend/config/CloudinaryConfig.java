package com.unsa.backend.config;

import java.util.HashMap;
import java.util.Map;

import com.cloudinary.Cloudinary;

import io.github.cdimascio.dotenv.Dotenv;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    public static final String CLOUD_NAME = Dotenv.configure().load().get("CLOUDINARY_CLOUD_NAME");
    public static final String API_KEY = Dotenv.configure().load().get("CLOUDINARY_API_KEY");
    private static final String API_SECRET = Dotenv.configure().load().get("CLOUDINARY_API_SECRET");

    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", CLOUD_NAME);
        config.put("api_key", API_KEY);
        config.put("api_secret", API_SECRET);
        return new Cloudinary(config);
    }
}
