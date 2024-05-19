package com.kaispread.grabber.application.slack;

import com.kaispread.grabber.base.support.IntegrationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class SlackNotificationSenderTest extends IntegrationTestSupport {

    @Autowired
    private SlackNotificationSender slackNotificationSender;

    @DisplayName("Slack 알림을 전송할 수 있다.")
    @Test
    void postToDailyChannel() {
        // given
        final String text = "hello";

        // when
        Mono<String> response = slackNotificationSender.postToTestChannel(new SlackMessage(text));

        // then
        StepVerifier.create(response)
            .expectNextCount(1)
            .verifyComplete();
    }
}