package com.kaispread.grabber.application.scrap.kakao;

import static com.kaispread.grabber.application.scrap.DefaultHeader.DEFAULT;

import com.kaispread.grabber.application.dto.company.CompanyDto;
import com.kaispread.grabber.application.dto.scrap.ScrapJdDto;
import com.kaispread.grabber.application.scrap.JobDescriptionScrapper;
import com.kaispread.grabber.base.support.IntegrationTestSupport;
import com.kaispread.grabber.domain.company.CompanyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class KakaoScrapperTest extends IntegrationTestSupport {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    JobDescriptionScrapper kakaoScrapper;

    @DisplayName("카카오 공고 데이터를 스크랩 할 수 있다.")
    @Test
    void scrap() {
        // given
        Flux<ScrapJdDto> scrap = kakaoScrapper.scrap(getCompanyDto(), DEFAULT.getMap())
            .doOnNext(System.out::println);

        // when
        StepVerifier.create(scrap)
            .expectNextMatches(ScrapJdDto::isValidDto)
            .thenCancel()
            .verify();
    }

    private CompanyDto getCompanyDto() {
        return CompanyDto.builder()
            .id("A001")
            .companyName("kakao")
            .serviceName("kakao talk")
            .uri("https://careers.kakao.com/public/api/job-list?part=TECHNOLOGY&company=KAKAO&page=1&size=100")
            .build();
    }
}