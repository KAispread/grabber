package com.kaispread.grabber.application.message;

import com.kaispread.grabber.application.dto.scrap.ScrapJdDto;
import com.kaispread.grabber.application.message.generator.NewEventMessageGenerator;
import com.kaispread.grabber.base.support.IntegrationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class DailyJdMessageGeneratorTest extends IntegrationTestSupport {

    @Autowired
    @Qualifier("dailyJdMessageGenerator")
    private NewEventMessageGenerator dailyJdMessageGenerator;

    @DisplayName("새로운 공고 이벤트를 바탕으로 알림 메시지를 생성할 수 있다.")
    @Test
    void generateMessage() {
        // given
        Flux<ScrapJdDto> events = getMockEvents();

        // when
        Mono<String> result = dailyJdMessageGenerator.generateMessage(events)
            .doOnNext(System.out::println);

        // then
        StepVerifier.create(result)
            .expectNextMatches(message ->  !message.isBlank())
            .verifyComplete();
    }

    @DisplayName("새로운 이벤트가 없을 경우 대체 문구를 생성한다.")
    @Test
    void generateMessageNoEvent() {
        // given
        Flux<ScrapJdDto> events = Flux.empty();

        // when
        Mono<String> result = dailyJdMessageGenerator.generateMessage(events)
            .doOnNext(System.out::println);

        // then
        StepVerifier.create(result)
            .expectNextMatches(message ->  message.contains("오늘은 새로운 공고를 찾지 못했습니다"))
            .verifyComplete();
    }

    private static Flux<ScrapJdDto> getMockEvents() {
        return Flux.just(
            ScrapJdDto.builder()
                .serviceName("토스")
                .companyName("Toss")
                .jdTitle("토스 백엔드 엔지니어 [수신/여신]")
                .jdUrl("mock.toss.url/1")
                .build(),
            ScrapJdDto.builder()
                .serviceName("토스")
                .companyName("Toss")
                .jdTitle("토스 프론트엔드 엔지니어 [수신/여신]")
                .jdUrl("mock.toss.url/2")
                .build(),
            ScrapJdDto.builder()
                .serviceName("네이버웹툰")
                .companyName("Naver")
                .jdTitle("네이버웹툰 Backend developer [결제]")
                .jdUrl("mock.naver.url/1")
                .build(),
            ScrapJdDto.builder()
                .serviceName("Hakuna")
                .companyName("Hyperconnect")
                .jdTitle("Hakuna Backend engineer")
                .jdUrl("mock.hyperconnect.url/1")
                .build()
        );
    }
}