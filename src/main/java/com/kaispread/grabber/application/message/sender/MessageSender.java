package com.kaispread.grabber.application.message.sender;

import com.kaispread.grabber.application.dto.scrap.ScrapJdDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MessageSender {
    Mono<Void> send(Flux<ScrapJdDto> scrapJdDtoFlux);
}
