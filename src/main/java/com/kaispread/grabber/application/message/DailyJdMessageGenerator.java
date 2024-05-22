package com.kaispread.grabber.application.message;

import com.kaispread.grabber.application.dto.scrap.ScrapJdDto;
import com.kaispread.grabber.application.slack.SlackChannel;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class DailyJdMessageGenerator extends JdMessageGenerator implements NewEventMessageGenerator {

    private static final SlackChannel CHANNEL = SlackChannel.DAILY_ALL;

    @Override
    public Mono<String> generateMessage(Flux<ScrapJdDto> scrapJdDtoFlux) {
        return scrapJdDtoFlux
            .groupBy(ScrapJdDto::companyName)
            .flatMap(groupedFlux -> groupedFlux
                .collectList()
                .map(list -> getMessagePerService(groupedFlux.key(), list))
            )
            .collectList()
            .filter(list -> !list.isEmpty())
            .switchIfEmpty(getNoEventMessage())
            .map(messagePerCompany -> getTotalMessage(CHANNEL.getChannelName(), messagePerCompany));
    }
}
