package com.kaispread.grabber.application.dto.scrap;

import com.kaispread.grabber.application.dto.company.CompanyDto;
import com.kaispread.grabber.application.dto.error.ApiCallScrapError;
import com.kaispread.grabber.application.dto.error.DefaultScrapError;
import com.kaispread.grabber.application.dto.error.JsonParseError;
import com.kaispread.grabber.application.dto.error.ScrapError;
import com.kaispread.grabber.domain.jd.JobDescription;
import com.kaispread.grabber.domain.jd.Position;
import com.kaispread.grabber.exception.ContainsCompanyDataException;
import com.kaispread.grabber.exception.convert.ConvertJobDescriptionException;
import com.kaispread.grabber.exception.external.DataApiCallException;
import com.kaispread.grabber.exception.json.DataJsonException;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.util.StringUtils;

@Builder
public record ScrapJdDto (
    @NotNull String companyId,
    @NotNull String jdId,
    @NotNull String jdUrl,
    @NotNull String jdTitle,
    @NotNull Position position,
    @NotNull String serviceName,
    @NotNull String serviceNameKr,

    String companyName,
    String jobProcess,
    String requiredSkill,
    String qualification,
    String location,
    String introduction,

    ScrapError error
) {

    public JobDescription toJdEntity() {
        if (isInvalidDto()) throw new ConvertJobDescriptionException();
        return JobDescription.builder()
            .companyId(companyId)
            .jobId(jdId)
            .url(jdUrl)
            .jobTitle(jdTitle)
            .jobPosition(position)
            .introduction(introduction)
            .jobProcess(jobProcess)
            .requiredSkill(requiredSkill)
            .qualification(qualification)
            .location(location)
            .build();
    }

    public boolean isError() {
        return error != null;
    }

    // 필수 데이터가 없다면 True 반환
    public boolean isInvalidDto() {
        return companyId == null ||
                position == null ||
                !StringUtils.hasText(jdId) ||
                !StringUtils.hasText(jdUrl) ||
                !StringUtils.hasText(jdTitle) ||
                !StringUtils.hasText(serviceName) ||
                isError();
    }

    public boolean isValidDto() {
        return !isInvalidDto();
    }

    public static ScrapJdDto createExceptionDto(final ContainsCompanyDataException e) {
        CompanyDto companyData = e.getCompanyData();
        if (e instanceof DataApiCallException) return createApiExceptionDto(companyData.serviceName(), companyData.uri());
        if (e instanceof DataJsonException) return createJsonExceptionDto(companyData.serviceName(), companyData.uri());
        return createDefaultExceptionDto(companyData.serviceName(), companyData.uri());
    }

    private static ScrapJdDto createApiExceptionDto(final String serviceName, final String uri) {
        return ScrapJdDto.builder()
            .companyId("EEEE")
            .error(ApiCallScrapError.builder()
                .serviceName(serviceName)
                .url(uri)
                .build()
            ).build();
    }

    private static ScrapJdDto createJsonExceptionDto(final String serviceName, final String uri) {
        return ScrapJdDto.builder()
            .companyId("EEEE")
            .error(JsonParseError.builder()
                .serviceName(serviceName)
                .url(uri)
                .build()
            ).build();
    }

    private static ScrapJdDto createDefaultExceptionDto(final String serviceName, final String uri) {
        return ScrapJdDto.builder()
            .companyId("EEEE")
            .error(DefaultScrapError.builder()
                .serviceName(serviceName)
                .url(uri)
                .build()
            ).build();
    }
}
