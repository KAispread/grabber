package com.kaispread.grabber.config;

import com.kaispread.grabber.config.r2dbc.R2dbcConnectionProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableConfigurationProperties({
    R2dbcConnectionProperties.class
})
@EnableScheduling
@EnableR2dbcAuditing
@EnableR2dbcRepositories
@Configuration
public class ApplicationConfig {

}
