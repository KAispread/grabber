package com.kaispread.grabber.domain.company;

import static com.kaispread.grabber.application.scrap.ScrapperType.KAKAO_CORE;

import com.kaispread.grabber.base.support.IntegrationTestSupport;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class CompanyRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private CompanyRepository repository;

    @AfterEach
    void tearDown() {
        repository.deleteAll().block();
    }

    @DisplayName("회사 데이터를 저장할 수 있다.")
    @Test
    void save() {
        // given
        Company company = Company.builder()
            .name("카카오")
            .serviceName("카카오코어")
            .recruitmentUrl("test.url")
            .scrapperType(KAKAO_CORE)
            .build();

        // when
        Mono<Company> saveCompanyMono = repository.save(company);

        // then
        StepVerifier.create(saveCompanyMono)
            .expectNextMatches(savedCompany -> savedCompany.getId() != null)
            .verifyComplete();

        Flux<Company> allCompanies = repository.findAll();
        StepVerifier.create(allCompanies)
            .expectNextMatches(savedCompany -> savedCompany.getName().equals("카카오"))
            .verifyComplete();
    }
}