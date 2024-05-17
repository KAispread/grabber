package com.kaispread.grabber.application.dto.scrap;

import com.kaispread.grabber.application.dto.error.ApiCallScrapError;
import com.kaispread.grabber.application.dto.error.JsonParseError;
import com.kaispread.grabber.application.dto.error.ScrapError;
import com.kaispread.grabber.domain.jd.Position;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.util.StringUtils;

@Builder
public record ScrapJdDto (
    @NotNull Long companyId,
    @NotNull String jdId,
    @NotNull String jdUrl,
    @NotNull String jdTitle,
    @NotNull Position position,
    @NotNull String serviceName,

    String companyName,
    String jobProcess,
    String requiredSkill,
    String qualification,
    String location,
    String introduction,

    ScrapError error
) {

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

    public static ScrapJdDto createApiExceptionDto(final String serviceName, final String uri) {
        return ScrapJdDto.builder()
            .error(ApiCallScrapError.builder()
                .serviceName(serviceName)
                .url(uri)
                .build()
            ).build();
    }

    public static ScrapJdDto createJsonExceptionDto(final String serviceName, final String uri) {
        return ScrapJdDto.builder()
            .error(JsonParseError.builder()
                .serviceName(serviceName)
                .url(uri)
                .build()
            ).build();
    }
}
