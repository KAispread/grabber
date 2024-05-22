package com.kaispread.grabber.application.message.sender;

import com.kaispread.grabber.application.dto.scrap.ScrapJdDto;
import com.kaispread.grabber.application.message.generator.NewEventMessageGenerator;
import com.kaispread.grabber.application.slack.SlackMessage;
import com.kaispread.grabber.application.slack.SlackNotificationSender;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class DailyMessageSender implements MessageSender {

    private final SlackNotificationSender slackNotificationSender;
    private final NewEventMessageGenerator newEventMessageGenerator;

    public DailyMessageSender(@Qualifier("dailyJdMessageGenerator") NewEventMessageGenerator newEventMessageGenerator,
                              SlackNotificationSender slackNotificationSender) {
        this.slackNotificationSender = slackNotificationSender;
        this.newEventMessageGenerator = newEventMessageGenerator;
    }

    @Override
    public Mono<Void> send(final Flux<ScrapJdDto> scrapJdDtoFlux) {
        return newEventMessageGenerator.generateMessage(scrapJdDtoFlux)
            .map(SlackMessage::new)
            // 새로운 공고 알림 전송
            .flatMap(slackNotificationSender::postToDailyChannel)
            .then();
    }
}
