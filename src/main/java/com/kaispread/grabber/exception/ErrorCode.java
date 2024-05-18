package com.kaispread.grabber.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    // on API call
    API_CALL_ERROR(500, "외부 API 호출중 문제가 발생했습니다. URI :: "),

    // on Scrapping (ex. json parsing ...)
    SCRAP_ERROR(500, "Scrapping 중 문제가 발생했습니다."),
    PARSE_ERROR(500, "Json 인스턴스로 변환 중 문제가 발생했습니다."),

    // on Type Convert
    CONVERT_JOB_DESCRIPTION(500, "JobDescription Entity로 변환하는데 실패했습니다.")
    ;

    private final int status;
    private final String message;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
