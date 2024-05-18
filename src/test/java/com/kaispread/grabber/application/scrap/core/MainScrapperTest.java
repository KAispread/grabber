package com.kaispread.grabber.application.scrap.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.kaispread.grabber.application.dto.company.CompanyDto;
import com.kaispread.grabber.application.dto.scrap.ScrapJdDto;
import com.kaispread.grabber.application.scrap.JobDescriptionScrapper;
import com.kaispread.grabber.application.scrap.ScrapperFactory;
import com.kaispread.grabber.application.scrap.ScrapperType;
import com.kaispread.grabber.domain.company.Company;
import com.kaispread.grabber.domain.company.CompanyRepository;
import com.kaispread.grabber.domain.event.ExceptionEvent;
import com.kaispread.grabber.domain.event.ExceptionEventRepository;
import com.kaispread.grabber.domain.jd.JobDescription;
import com.kaispread.grabber.domain.jd.JobDescriptionRepository;
import com.kaispread.grabber.domain.jd.Position;
import com.kaispread.grabber.exception.external.DataApiCallException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
class MainScrapperTest {

    @Mock private ScrapperFactory scrapperFactory;
    @Mock private JobDescriptionRepository jdRepository;
    @Mock private CompanyRepository companyRepository;
    @Mock private ExceptionEventRepository exceptionEventRepository;

    @InjectMocks
    private MainScrapper mainScrapper;

    @DisplayName("새로운 공고와 예외 이벤트가 각각 저장된다.")
    @Test
    void save_new_jd_and_exception_event() {
        // given
        given(scrapperFactory.getJdScrapper(any()))
            .willReturn(new MockJdAndErrorScrapper());
        given(companyRepository.findAll())
            .willReturn(getMockCompany());
        given(exceptionEventRepository.save(any()))
            .willReturn(Mono.just(ExceptionEvent.builder().build()));
        given(jdRepository.save(any()))
            .willReturn(Mono.just(JobDescription.builder().build()));
        given(jdRepository.findByCompanyIdAndJobId(any(), any()))
            .willReturn(Flux.empty());

        // when
        Flux<ScrapJdDto> scrapJdDtoFlux = mainScrapper.runScrapping();

        // then
        StepVerifier.create(scrapJdDtoFlux)
            .expectNextMatches(scrapJdDto -> scrapJdDto.jdId().equals("JE0001"))
            .verifyComplete();

        verify(exceptionEventRepository, times(1)).save(any());
        verify(jdRepository, times(1)).save(any());
    }

    @DisplayName("새로운 공고 데이터만 저장된다.")
    @Test
    void save_success_scrap_exception() {
        // given
        final List<String> expectJdIdList = new ArrayList<>(List.of("KA0002", "H0001", "H0002"));

        given(scrapperFactory.getJdScrapper(any()))
            .willReturn(new MockJdScrapper());
        given(companyRepository.findAll())
            .willReturn(getMockCompany());
        given(jdRepository.save(any()))
            .willReturn(Mono.just(JobDescription.builder().build()));

        given(jdRepository.findByCompanyIdAndJobId(eq("A001"), anyList()))
            .willReturn(Flux.just("KA0001"));
        given(jdRepository.findByCompanyIdAndJobId(eq("A002"), anyList()))
            .willReturn(Flux.empty());

        // when
        Flux<ScrapJdDto> scrapJdDtoFlux = mainScrapper.runScrapping();

        // then
        StepVerifier.create(scrapJdDtoFlux)
            .thenConsumeWhile(scrapJdDto -> expectJdIdList.remove(scrapJdDto.jdId()))
            .verifyComplete();

        assertThat(expectJdIdList).isEmpty();
        verify(exceptionEventRepository, times(0)).save(any());
        verify(jdRepository, times(3)).save(any());
    }

    private Flux<Company> getMockCompany() {
        return Flux.just(
            Company.builder()
                .id("A001")
                .name("kakao core")
                .serviceName("kakao talk")
                .recruitmentUrl("https://mock.kakao.core")
                .scrapperType(ScrapperType.KAKAO_CORE)
                .build(),
            Company.builder()
                .id("A002")
                .name("hyper connect")
                .serviceName("Azar")
                .recruitmentUrl("https://mock.hyperconnet.core")
                .scrapperType(ScrapperType.HYPERCONNECT)
                .build()
        );
    }

    static class MockJdAndErrorScrapper implements JobDescriptionScrapper {
        @Override
        public Flux<ScrapJdDto> scrap(CompanyDto companyDto, Map<String, String> header) {
            if (companyDto.scrapperType() == ScrapperType.KAKAO_CORE)
                return Flux.error(new DataApiCallException(companyDto));

            return Flux.just(ScrapJdDto.builder()
                    .companyId("A002")
                    .jdId("JE0001")
                    .jdUrl("https://mock.jd.1")
                    .jdTitle("Hakuna Backend Developer")
                    .position(Position.BACKEND)
                    .serviceName("Hakuna")
                    .companyName("hyper connect")
                .build());
        }
    }

    static class MockJdScrapper implements JobDescriptionScrapper {
        @Override
        public Flux<ScrapJdDto> scrap(CompanyDto companyDto, Map<String, String> header) {
            if (companyDto.scrapperType() == ScrapperType.KAKAO_CORE)
                return Flux.just(
                    ScrapJdDto.builder()
                        .companyId("A001")
                        .jdId("KA0001")
                        .jdUrl("https://mock.jd.1")
                        .jdTitle("백엔드 개발자")
                        .position(Position.BACKEND)
                        .serviceName("kakao core")
                        .companyName("kakao")
                        .build(),
                    ScrapJdDto.builder()
                        .companyId("A001")
                        .jdId("KA0002")
                        .jdUrl("https://mock.jd.2")
                        .jdTitle("프론트엔드 개발자")
                        .position(Position.FRONTEND)
                        .serviceName("kakao core")
                        .companyName("kakao")
                        .build()
                );

            return Flux.just(
                ScrapJdDto.builder()
                    .companyId("A002")
                    .jdId("H0001")
                    .jdUrl("https://mock.ha.1")
                    .jdTitle("백엔드 개발자")
                    .position(Position.BACKEND)
                    .serviceName("Hakuna")
                    .companyName("hyperconnect")
                    .build(),
                ScrapJdDto.builder()
                    .companyId("A001")
                    .jdId("H0002")
                    .jdUrl("https://mock.ha.2")
                    .jdTitle("프론트엔드 개발자")
                    .position(Position.FRONTEND)
                    .serviceName("Azar")
                    .companyName("hyperconnect")
                    .build()
            );
        }
    }
}