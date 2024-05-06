package com.kaispread.grabber.application.dto.error;

import lombok.Builder;

@Builder
public record ApiCallScrapError (
    String serviceName,
    String url
) implements ScrapError {

    @Override
    public String getErrorMessage() {
        return String.format("API call Error:: Service name=%s / url=%s", serviceName, url);
    }
}
