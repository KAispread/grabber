package com.kaispread.grabber.application.api;

import com.kaispread.grabber.base.support.IntegrationTestSupport;
import com.kaispread.grabber.exception.external.ApiCallException;
import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class SimpleApiCallerTest extends IntegrationTestSupport {
    @Autowired
    private ApiCaller apiCaller;

    @DisplayName("외부 API 호출에 성공한다.")
    @Test
    void api_call_success() {
        // given
        String uri = "https://careers.kakao.com/public/api/job-list?skillSet=&part=TECHNOLOGY&company=KAKAO&employeeType=";
        Mono<String> monoResp = apiCaller.get(uri, String.class);

        // when & then
        StepVerifier.create(monoResp)
            .expectNextMatches(Objects::nonNull)
            .verifyComplete();
    }

    @DisplayName("유효하지 않은 API는 호출할 수 없다.")
    @Test
    void api_call_with_invalid_uri() {
        // given
        String invalidUri = "https://invalid.api.call";
        Mono<String> monoResp = apiCaller.get(invalidUri, String.class);

        // when & then
        StepVerifier.create(monoResp)
            .verifyError(ApiCallException.class);
    }
}