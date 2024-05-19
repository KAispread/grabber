package com.kaispread.grabber.config.slack;

import com.kaispread.grabber.application.slack.SlackNotificationSender;
import io.netty.channel.ChannelOption;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.netty.http.client.HttpClient;

@Configuration
public class SlackConfig {

    @Bean
    public SlackNotificationSender slackNotificationSender() {
        HttpClient client = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 2_000);

        WebClient webClient = WebClient.builder()
            .baseUrl("https://hooks.slack.com/services")
            .clientConnector(new ReactorClientHttpConnector(client))
            .build();

        WebClientAdapter webClientAdapter = WebClientAdapter.create(webClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(webClientAdapter).build();

        return factory.createClient(SlackNotificationSender.class);
    }
}
