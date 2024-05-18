package com.kaispread.grabber.application.scrap.core;

import com.kaispread.grabber.application.dto.company.CompanyDto;
import com.kaispread.grabber.application.dto.scrap.ScrapJdDto;
import com.kaispread.grabber.application.scrap.DefaultHeader;
import com.kaispread.grabber.application.scrap.ScrapperFactory;
import com.kaispread.grabber.domain.company.CompanyRepository;
import com.kaispread.grabber.domain.event.ExceptionEvent;
import com.kaispread.grabber.domain.event.ExceptionEventRepository;
import com.kaispread.grabber.domain.jd.JobDescriptionRepository;
import com.kaispread.grabber.exception.ContainsCompanyDataException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

/*
* **MainScrapper Process**
* 1. company 테이블에 적재된 공고 URI로부터 공고 데이터 스크랩
*    a. 스크랩 도중 예외 발생시 exception_event 테이블에 이벤트 저장
* 2. job_description 테이블을 조회하여 새로운 공고만 Downstream emit
* 3. 새로운 공고 저장
* */
@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class MainScrapper {

    private static final String NEW_JOB_DESCRIPTION_LOG_FORMAT = "New Job Description has saved :: companyId = {}, position = {}, title = {}";
    private static final String SCRAP_ERROR_LOG_FORMAT = "Scrap Error :: serviceName = {}, message = {}";

    private final ScrapperFactory scrapperFactory;
    private final CompanyRepository companyRepository;
    private final JobDescriptionRepository jdRepository;
    private final ExceptionEventRepository exceptionEventRepository;

    public Flux<ScrapJdDto> runScrapping() {
        return companyRepository.findAll()
            .map(CompanyDto::from)
            .flatMap(companyDto -> scrapperFactory.getJdScrapper(companyDto.scrapperType())
                .scrap(companyDto, DefaultHeader.DEFAULT.getMap())
                // 에러 이벤트 저장 및 Publisher 대체
                .onErrorResume(ContainsCompanyDataException.class, this::getErrorScrapJdDto))
            .filter(ScrapJdDto::isValidDto)
            .groupBy(ScrapJdDto::companyId)
            .flatMap(scrapJdDto -> scrapJdDto.collectList()
                .flatMapMany(scrapJdDtoList -> filterNewJobDescription(scrapJdDtoList, scrapJdDto.key())))
            // 새로운 공고 저장
            .flatMap(newJdDto -> jdRepository.save(newJdDto.toJdEntity())
                .doOnNext(savedJd -> log.info(NEW_JOB_DESCRIPTION_LOG_FORMAT, savedJd.getCompanyId(), savedJd.getJobPosition(), savedJd.getJobTitle()))
                .thenReturn(newJdDto));
    }

    private Flux<ScrapJdDto> filterNewJobDescription(final List<ScrapJdDto> scrapJdDtoList, final String companyId) {
        List<String> list = scrapJdDtoList.stream()
            .map(ScrapJdDto::jdId)
            .toList();

        return jdRepository.findByCompanyIdAndJobId(companyId, list)
            .collectList()
            .flatMapMany(savedJdIds -> Flux.fromStream(
                scrapJdDtoList.stream()
                    .filter(scrapJdDto -> !savedJdIds.contains(scrapJdDto.jdId()))));
    }

    private Flux<ScrapJdDto> getErrorScrapJdDto(final ContainsCompanyDataException exception) {
        return exceptionEventRepository.save(getExceptionEvent(exception))
            .thenMany(
                Flux.just(ScrapJdDto.createExceptionDto(exception))
                    .doOnNext(errorDto -> log.warn(SCRAP_ERROR_LOG_FORMAT,
                        exception.getCompanyData().serviceName(),
                        errorDto.error().getErrorMessage()))
            );
    }

    private ExceptionEvent getExceptionEvent(final ContainsCompanyDataException e) {
        return ExceptionEvent.builder()
            .companyId(e.getCompanyData().id())
            .exception(e.getClass().getSimpleName())
            .description(e.getMessage())
            .build();
    }
}
