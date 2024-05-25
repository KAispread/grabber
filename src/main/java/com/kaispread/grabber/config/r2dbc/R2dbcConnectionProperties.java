package com.kaispread.grabber.config.r2dbc;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;

@Profile("!prod")
@Getter
@RequiredArgsConstructor
@ConfigurationProperties("r2dbc")
public class R2dbcConnectionProperties {

    private final String url;
    private final Integer port;
    private final String username;
    private final String password;
    private final String database;
}
