package com.kaispread.grabber.domain.jd;

import com.kaispread.grabber.application.scrap.ScrapperType;
import com.kaispread.grabber.base.support.IntegrationTestSupport;
import com.kaispread.grabber.domain.company.Company;
import com.kaispread.grabber.domain.company.CompanyRepository;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class JobDescriptionRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private JobDescriptionRepository jdRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @AfterEach
    void tearDown() {
        jdRepository.deleteAll().block();
        companyRepository.deleteAll().block();
    }

    @DisplayName("이미 존재하는 공고의 job_id 를 조회할 수 있다.")
    @Test
    void findByCompanyIdAndJobId() {
        // given
        saveCompany().block();
        saveJdList().collectList().block();

        // when
        Flux<String> findFlux = jdRepository.findByCompanyIdAndJobId("A001", List.of("KA002", "KA003", "KA004"));

        // then
        StepVerifier.create(findFlux)
            .expectNextMatches("KA002"::equals)
            .expectNextMatches("KA003"::equals)
            .verifyComplete();
    }

    private Mono<Company> saveCompany() {
        return companyRepository.save(Company.builder()
                .id("A001")
                .name("kakao")
                .serviceName("kakao core")
                .recruitmentUrl("mock.url")
                .scrapperType(ScrapperType.KAKAO_CORE)
                .build());
    }

    private Flux<JobDescription> saveJdList() {
        return jdRepository.saveAll(List.of(
            JobDescription.builder()
                .companyId("A001")
                .jobId("KA001")
                .url("mock.url.1")
                .jobTitle("Backend")
                .jobPosition(Position.BACKEND)
                .build(),
            JobDescription.builder()
                .companyId("A001")
                .jobId("KA002")
                .url("mock.url.2")
                .jobTitle("Frontend")
                .jobPosition(Position.FRONTEND)
                .build(),
            JobDescription.builder()
                .companyId("A001")
                .jobId("KA003")
                .url("mock.url.3")
                .jobTitle("Devops")
                .jobPosition(Position.DEVOPS)
                .build()
        ));
    }
}