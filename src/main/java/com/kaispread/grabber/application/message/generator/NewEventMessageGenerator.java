package com.kaispread.grabber.application.message.generator;

import com.kaispread.grabber.application.dto.scrap.ScrapJdDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface NewEventMessageGenerator {
    Mono<String> generateMessage(Flux<ScrapJdDto> scrapJdDtoFlux);
}
