package com.kaispread.grabber.config;

import com.kaispread.grabber.config.r2dbc.R2dbcConnectionProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties({
    R2dbcConnectionProperties.class
})
@Configuration
public class ApplicationConfig {

}
