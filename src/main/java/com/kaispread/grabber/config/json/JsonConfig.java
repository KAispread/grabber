package com.kaispread.grabber.config.json;

import org.json.simple.parser.JSONParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonConfig {
    @Bean
    public JSONParser jsonParser() {
        return new JSONParser();
    }
}
