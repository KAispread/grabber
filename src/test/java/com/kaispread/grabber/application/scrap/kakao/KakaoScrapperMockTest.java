package com.kaispread.grabber.application.scrap.kakao;

import static com.kaispread.grabber.application.scrap.DefaultHeader.DEFAULT;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import com.kaispread.grabber.application.api.ApiCaller;
import com.kaispread.grabber.application.dto.company.CompanyDto;
import com.kaispread.grabber.application.dto.scrap.ScrapJdDto;
import com.kaispread.grabber.application.json.kakao.KakaoJsonParser;
import com.kaispread.grabber.exception.ContainsCompanyDataException;
import com.kaispread.grabber.exception.external.ApiCallException;
import com.kaispread.grabber.exception.external.DataApiCallException;
import com.kaispread.grabber.exception.json.DataJsonException;
import com.kaispread.grabber.exception.json.JsonParseException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class KakaoScrapperMockTest {

    @Mock
    ApiCaller apiCaller;

    @Mock
    KakaoJsonParser parser;

    @InjectMocks
    KakaoScrapper kakaoScrapper;

    @DisplayName("API 호출에서 예외가 발생할 경우 Downstream에서 잡을 수 있다.")
    @Test
    void api_fail() {
        // given
        given(apiCaller.get(any(), any()))
            .willReturn(Mono.error(new ApiCallException("www.naver.com")));

        // when
        Flux<ScrapJdDto> scrap = kakaoScrapper.scrap(getCompanyDto(), DEFAULT.getMap())
            .onErrorResume(DataApiCallException.class, this::getErrorScrapJdDto);

        // then
        StepVerifier.create(scrap)
            .expectNextMatches(ScrapJdDto::isError)
            .verifyComplete();
    }

    @DisplayName("문자열을 JSONObject로 변환하는 과정에서 예외가 발생할 경우 Downstream에서 잡을 수 있다.")
    @Test
    void parseToJsonObject_fail() {
        // given
        given(apiCaller.get(any(), any()))
            .willReturn(Mono.just("scrap data"));
        given(parser.parseToJsonObject(anyString()))
            .willThrow(JsonParseException.class);

        // when
        Flux<ScrapJdDto> scrap = kakaoScrapper.scrap(getCompanyDto(), DEFAULT.getMap())
            .onErrorResume(DataJsonException.class, this::getErrorScrapJdDto);

        // then
        StepVerifier.create(scrap)
            .expectNextMatches(ScrapJdDto::isError)
            .verifyComplete();
    }

    @DisplayName("JSONObject에서 JSONArray로 변환하는 과정에서 예외가 발생할 경우 Downstream에서 잡을 수 있다.")
    @Test
    void parseToJsonArray_fail() {
        // given
        given(apiCaller.get(any(), any()))
            .willReturn(Mono.just("scrap data"));
        given(parser.parseToJsonObject(anyString()))
            .willReturn(new JSONObject());
        given(parser.parseToJsonArray(any(), anyString()))
            .willThrow(JsonParseException.class);

        // when
        Flux<ScrapJdDto> scrap = kakaoScrapper.scrap(getCompanyDto(), DEFAULT.getMap())
            .onErrorResume(DataJsonException.class, this::getErrorScrapJdDto);

        // then
        StepVerifier.create(scrap)
            .expectNextMatches(ScrapJdDto::isError)
            .verifyComplete();
    }

    @DisplayName("JSONArray에서 Dto로 변환하는 과정에서 예외가 발생할 경우 Downstream에서 잡을 수 있다.")
    @Test
    void parseToJdDto_fail() {
        // given
        given(apiCaller.get(any(), any()))
            .willReturn(Mono.just("scrap data"));
        given(parser.parseToJsonObject(anyString()))
            .willReturn(new JSONObject());
        given(parser.parseToJsonArray(any(), anyString()))
            .willReturn(new JSONArray());
        given(parser.parseToJdDto(any(), any()))
            .willReturn(Flux.error(RuntimeException::new));

        // when
        Flux<ScrapJdDto> scrap = kakaoScrapper.scrap(getCompanyDto(), DEFAULT.getMap())
            .onErrorResume(DataJsonException.class, this::getErrorScrapJdDto);

        // then
        StepVerifier.create(scrap)
            .expectNextMatches(ScrapJdDto::isError)
            .verifyComplete();
    }

    private CompanyDto getCompanyDto() {
        return CompanyDto.builder()
            .id("A001")
            .companyName("카카오")
            .serviceName("카카오 코어")
            .build();
    }

    private Flux<ScrapJdDto> getErrorScrapJdDto(final ContainsCompanyDataException exception) {
        return Flux.just(ScrapJdDto.createExceptionDto(exception));
    }
}