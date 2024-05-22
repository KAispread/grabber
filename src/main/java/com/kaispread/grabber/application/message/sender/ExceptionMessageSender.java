package com.kaispread.grabber.application.message.sender;

import reactor.core.publisher.Mono;

public interface ExceptionMessageSender {
    Mono<Void> send();
}
