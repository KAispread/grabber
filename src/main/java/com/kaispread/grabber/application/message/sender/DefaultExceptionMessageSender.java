package com.kaispread.grabber.application.message.sender;

import static com.kaispread.grabber.utils.time.CurrentTimeGenerator.MONTH_DAY_TIME;

import com.kaispread.grabber.application.message.generator.ExceptionEventMessageGenerator;
import com.kaispread.grabber.application.slack.SlackMessage;
import com.kaispread.grabber.application.slack.SlackNotificationSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class DefaultExceptionMessageSender implements ExceptionMessageSender {

    private static final String MESSAGE_SENT_LOG_FORMAT = "TIME : {} :: message has been sent to slack channel ({})";

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
            .doOnNext(next -> log.info(MESSAGE_SENT_LOG_FORMAT, MONTH_DAY_TIME.getCurrentTime(), "DAILY"))
            .then();
    }
}
