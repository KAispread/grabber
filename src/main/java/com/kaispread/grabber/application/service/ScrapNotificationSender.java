package com.kaispread.grabber.application.service;

import com.kaispread.grabber.application.dto.scrap.ScrapJdDto;
import com.kaispread.grabber.application.message.sender.ExceptionMessageSender;
import com.kaispread.grabber.application.message.sender.MessageSender;
import com.kaispread.grabber.application.scrap.core.MainScrapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ScrapNotificationSender {

    private final MainScrapper mainScrapper;
    private final ExceptionMessageSender exceptionMessageSender;
    private final MessageSender dailyMessageSender;

    public ScrapNotificationSender(MainScrapper mainScrapper,
                                   ExceptionMessageSender exceptionMessageSender,
                                   @Qualifier("dailyMessageSender") MessageSender dailyMessageSender) {
        this.mainScrapper = mainScrapper;
        this.exceptionMessageSender = exceptionMessageSender;
        this.dailyMessageSender = dailyMessageSender;
    }

    public Mono<Void> scrapAndSend() {
        Flux<ScrapJdDto> scrapJdDtoFlux = mainScrapper.runScrapping();

        return dailyMessageSender.send(scrapJdDtoFlux)
            .then(exceptionMessageSender.send());
    }
}
