package com.kaispread.grabber.application.message.sender;

import com.kaispread.grabber.application.message.generator.ExceptionEventMessageGenerator;
import com.kaispread.grabber.application.slack.SlackMessage;
import com.kaispread.grabber.application.slack.SlackNotificationSender;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class DefaultExceptionMessageSender implements ExceptionMessageSender {

    private final SlackNotificationSender slackNotificationSender;
    private final ExceptionEventMessageGenerator exceptionEventMessageGenerator;

    public DefaultExceptionMessageSender(SlackNotificationSender slackNotificationSender,
                                    ExceptionEventMessageGenerator exceptionEventMessageGenerator) {
        this.slackNotificationSender = slackNotificationSender;
        this.exceptionEventMessageGenerator = exceptionEventMessageGenerator;
    }

    @Override
    public Mono<Void> send() {
        return exceptionEventMessageGenerator.generateExceptionMessage()
            .map(SlackMessage::new)
            .flatMap(slackNotificationSender::postToExceptionChannel)
            .then();
    }
}
