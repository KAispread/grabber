package com.kaispread.grabber.application.service;

import static com.kaispread.grabber.utils.time.CurrentTimeGenerator.MONTH_DAY_TIME;

import com.kaispread.grabber.application.dto.scrap.ScrapJdDto;
import com.kaispread.grabber.application.message.sender.ExceptionMessageSender;
import com.kaispread.grabber.application.message.sender.MessageSender;
import com.kaispread.grabber.application.scrap.core.MainScrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ScrapNotificationSender {

    private static final String END_SCRAP_LOG_FORMAT = "TIME : {} :: scrap task ended";

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
        Flux<ScrapJdDto> scrapJdDtoFlux = mainScrapper.runScrapping()
            .doOnNext(next -> log.info(END_SCRAP_LOG_FORMAT, MONTH_DAY_TIME.getCurrentTime()));

        return dailyMessageSender.send(scrapJdDtoFlux)
            .then(exceptionMessageSender.send());
    }
}
