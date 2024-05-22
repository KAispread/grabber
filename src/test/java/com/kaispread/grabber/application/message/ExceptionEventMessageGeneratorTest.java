package com.kaispread.grabber.application.message;

import com.kaispread.grabber.application.scrap.ScrapperType;
import com.kaispread.grabber.base.support.IntegrationTestSupport;
import com.kaispread.grabber.domain.company.Company;
import com.kaispread.grabber.domain.company.CompanyRepository;
import com.kaispread.grabber.domain.event.ExceptionEvent;
import com.kaispread.grabber.domain.event.ExceptionEventRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class ExceptionEventMessageGeneratorTest extends IntegrationTestSupport {

    @Autowired
    ExceptionEventMessageGenerator exceptionEventMessageGenerator;

    @Autowired
    ExceptionEventRepository exceptionEventRepository;

    @Autowired
    CompanyRepository companyRepository;

    @BeforeEach
    void setUp() {
        companyRepository.save(Company.builder()
                .id("A001")
                .name("Kakao")
                .serviceName("kakao talk")
                .serviceNameKr("카카오톡")
                .recruitmentUrl("mock.kakao.url")
                .scrapperType(ScrapperType.KAKAO_CORE)
            .build()).block();

        companyRepository.save(Company.builder()
            .id("A002")
            .name("Hyperconnect")
            .serviceName("Azar")
            .serviceNameKr("아자르")
            .recruitmentUrl("mock.azar.url")
            .scrapperType(ScrapperType.HYPERCONNECT)
            .build()).block();

        exceptionEventRepository.save(
            ExceptionEvent.builder()
                .companyId("A001")
                .exception("NullPointException")
                .description("널일 수 없습니다")
                .build()).block();

        exceptionEventRepository.save(
            ExceptionEvent.builder()
                .companyId("A002")
                .exception("IllegalArgumentException")
                .description("유효하지 않은 값입니다.")
                .build()).block();
    }

    @AfterEach
    void tearDown() {
        exceptionEventRepository.deleteAll().block();
        companyRepository.deleteAll().block();
    }

    @DisplayName("예외 메시지를 생성할 수 있다.")
    @Test
    void generateMessage() {
        // when
        Mono<String> exceptionMessage = exceptionEventMessageGenerator.generateExceptionMessage()
            .doOnNext(System.out::println);

        // then
        StepVerifier.create(exceptionMessage)
            .expectNextMatches(message -> !message.isBlank())
            .verifyComplete();
    }

    @DisplayName("예외가 발생하지 않았을 경우 대체 메시지를 반환한다.")
    @Test
    void generateMessage_with_no_event() {
        // given
        exceptionEventRepository.deleteAll().block();

        // when
        Mono<String> exceptionMessage = exceptionEventMessageGenerator.generateExceptionMessage()
            .doOnNext(System.out::println);

        // then
        StepVerifier.create(exceptionMessage)
            .expectNextMatches(message -> message.contains("* 아무 예외가 발생하지 않았습니다 *"))
            .verifyComplete();
    }
}