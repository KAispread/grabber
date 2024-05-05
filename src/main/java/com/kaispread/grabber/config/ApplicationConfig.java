package com.kaispread.grabber.config;

import com.kaispread.grabber.config.r2dbc.R2dbcConnectionProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@EnableConfigurationProperties({
    R2dbcConnectionProperties.class
})
@EnableR2dbcRepositories
@Configuration
public class ApplicationConfig {

}
