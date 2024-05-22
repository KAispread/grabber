package com.kaispread.grabber.application.message.generator;

import com.kaispread.grabber.domain.company.Company;
import com.kaispread.grabber.domain.company.CompanyRepository;
import com.kaispread.grabber.domain.event.ExceptionEvent;
import com.kaispread.grabber.domain.event.ExceptionEventRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class ExceptionEventMessageGenerator {

    private static final String HEAD_LINE = "[%s 예외 발생 현황]";
    private static final String NO_EVENT = "* 아무 예외가 발생하지 않았습니다 *";
    private static final String EXCEPTION_MESSAGE_FORMAT = "* %s * \n url = %s \n exception = %s \n description = %s";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("M월 d일");

    private final ExceptionEventRepository exceptionEventRepository;
    private final CompanyRepository companyRepository;

    public Mono<String> generateExceptionMessage() {
        LocalDate findDate = LocalDate.now();

        return exceptionEventRepository.findByCreatedDate(findDate)
            .flatMap(exceptionEvent ->
                companyRepository.findById(exceptionEvent.getCompanyId())
                    .map(company -> ExceptionCompanyDto.of(exceptionEvent, company)))
            .map(this::getExceptionMessage)
            .collectList()
            .filter(list -> !list.isEmpty())
            .switchIfEmpty(getNoEventMessage())
            .map(this::getTotalMessage);
    }

    private String getHeadLine() {
        String todayDate = LocalDate.now().format(DATE_FORMAT);
        return String.format(HEAD_LINE, todayDate);
    }

    private String getExceptionMessage(final ExceptionCompanyDto dto) {
        return String.format(EXCEPTION_MESSAGE_FORMAT,
            dto.serviceName,
            dto.recruitmentUrl,
            dto.exception,
            dto.description);
    }

    private Mono<List<String>> getNoEventMessage() {
        return Mono.just(List.of(NO_EVENT));
    }

    private String getTotalMessage(List<String> list) {
        return String.join("\n",
            getHeadLine(),
            String.join("\n\n", list));
    }

    record ExceptionCompanyDto (
        String serviceName,
        String recruitmentUrl,
        String exception,
        String description
    ) {
        public static ExceptionCompanyDto of(final ExceptionEvent exceptionEvent, final Company company) {
            return new ExceptionCompanyDto(company.getServiceName(),
                                            company.getRecruitmentUrl(),
                                            exceptionEvent.getException(),
                                            exceptionEvent.getDescription());
        }
    }
}
