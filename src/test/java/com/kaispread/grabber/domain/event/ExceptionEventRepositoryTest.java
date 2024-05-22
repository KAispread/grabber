package com.kaispread.grabber.domain.event;

import com.kaispread.grabber.base.support.IntegrationTestSupport;
import java.time.LocalDate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class ExceptionEventRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private ExceptionEventRepository exceptionEventRepository;

    @BeforeEach
    void setUp() {
        exceptionEventRepository.save(ExceptionEvent.builder()
            .exception("NullPointException")
            .description("널이 아니여아합니다.")
            .companyId("A001")
            .build()).block();
    }

    @AfterEach
    void tearDown() {
        exceptionEventRepository.deleteAll().block();
    }

    @DisplayName("날짜를 기준으로 데이터를 조회할 수 있다.")
    @Test
    void findByCreatedDate() {
        // given
        LocalDate time = LocalDate.now();

        // when
        Flux<ExceptionEvent> findedFlux = exceptionEventRepository.findByCreatedDate(time);

        // then
        StepVerifier.create(findedFlux)
            .expectNextMatches(data -> data.getCompanyId().equals("A001"))
            .verifyComplete();
    }
}