package com.unsa.backend.config;

import java.util.HashMap;
import java.util.Map;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    private static final String CLOUD_NAME = "ddto4lnfz";
    private static final String API_KEY = "321291374866932";
    private static final String API_SECRET = "G2gxrLA7VxiwNXpgUO_8MPDR_vs";

    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", CLOUD_NAME);
        config.put("api_key", API_KEY);
        config.put("api_secret", API_SECRET);
        return new Cloudinary(config);
    }
}
