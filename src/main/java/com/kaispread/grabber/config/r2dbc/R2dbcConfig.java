package com.kaispread.grabber.config.r2dbc;

import io.r2dbc.spi.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import org.mariadb.r2dbc.MariadbConnectionConfiguration;
import org.mariadb.r2dbc.MariadbConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@RequiredArgsConstructor
@EnableR2dbcRepositories
@Configuration
public class R2dbcConfig extends AbstractR2dbcConfiguration {

    private final R2dbcConnectionProperties r2dbcConnectionProperties;

    @Override
    @Bean
    public ConnectionFactory connectionFactory() {
        MariadbConnectionConfiguration conf = MariadbConnectionConfiguration.builder()
            .host(r2dbcConnectionProperties.getUrl())
            .port(r2dbcConnectionProperties.getPort())
            .username(r2dbcConnectionProperties.getUsername())
            .password(r2dbcConnectionProperties.getPassword())
            .database(r2dbcConnectionProperties.getDatabase())
            .build();
        return new MariadbConnectionFactory(conf);
    }
}
