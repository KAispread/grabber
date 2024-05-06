package com.kaispread.grabber.application.api;

import com.kaispread.grabber.exception.external.ApiCallException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Component
public class SimpleApiCaller implements ApiCaller {

    private final WebClient webClient;

    @Override
    public <T> Mono<T> get(String uri, Class<T> responseType) {
        return webClient.get()
            .uri(uri)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->  Mono.error(new ApiCallException()))
            .onStatus(HttpStatusCode::is5xxServerError, clientResponse ->  Mono.error(new ApiCallException()))
            .bodyToMono(responseType);
    }
}
