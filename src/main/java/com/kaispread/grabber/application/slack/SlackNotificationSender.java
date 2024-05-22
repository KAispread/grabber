package com.kaispread.grabber.application.slack;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;

@HttpExchange(contentType = "application/json")
public interface SlackNotificationSender {

    @PostExchange(url = "/T073P744H8F/B0740SGGB61/42td9bNDWtLfuGDyPkeTOnrv")
    Mono<String> postToDailyChannel(@RequestBody SlackMessage message);

    @PostExchange(url = "/T073P744H8F/B0743QKC9DG/4kGa3j5TQqtT8PYqEEedcAvk")
    Mono<String> postToTestChannel(@RequestBody SlackMessage message);

    @PostExchange(url = "/T073P744H8F/B074PKVCG3E/ctO4CnQH5B6iPIjugvyuovPv")
    Mono<String> postToExceptionChannel(@RequestBody SlackMessage message);
}
