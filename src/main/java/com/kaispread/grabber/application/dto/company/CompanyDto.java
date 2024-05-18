package com.kaispread.grabber.application.dto.company;

import com.kaispread.grabber.application.scrap.ScrapperType;
import com.kaispread.grabber.domain.company.Company;
import lombok.Builder;

@Builder
public record CompanyDto (
    String id,
    String companyName,
    String serviceName,
    String uri,
    ScrapperType scrapperType
) {
    public static CompanyDto from(final Company company) {
        return CompanyDto.builder()
            .id(company.getId())
            .companyName(company.getName())
            .serviceName(company.getServiceName())
            .uri(company.getRecruitmentUrl())
            .scrapperType(company.getScrapperType())
            .build();
    }
}
