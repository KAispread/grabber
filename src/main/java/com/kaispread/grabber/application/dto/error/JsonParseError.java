package com.kaispread.grabber.application.dto.error;

import lombok.Builder;

@Builder
public record JsonParseError (
    String serviceName,
    String url
) implements ScrapError {

    @Override
    public String getErrorMessage() {
        return String.format("Json Parse Error:: Service name=%s / url=%s", serviceName, url);
    }
}
