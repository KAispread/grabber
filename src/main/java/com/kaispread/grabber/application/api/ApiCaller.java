package com.kaispread.grabber.application.api;

import reactor.core.publisher.Mono;

public interface ApiCaller {
    <T> Mono<T> get(final String uri, final Class<T> responseType);
}
