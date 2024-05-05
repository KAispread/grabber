package com.kaispread.grabber.config.h2;

import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Profile("develop")
@Component
public class H2Config {
    @Value("${spring.h2.console.port}")
    private Integer port;
    private Server webServer;

    @EventListener(ContextRefreshedEvent.class)
    public void start() throws SQLException {
        log.info("start H2 database ...");
        this.webServer = Server.createWebServer("-webPort", port.toString()).start();
    }

    @EventListener(ContextClosedEvent.class)
    public void stop() {
        log.info("stop H2 database ...");
        this.webServer.stop();
    }
}
