package com.kaispread.grabber.exception.external;

import com.kaispread.grabber.exception.CustomException;
import com.kaispread.grabber.exception.ErrorCode;

public class ApiCallException extends CustomException {
    private static final ErrorCode errorCode = ErrorCode.API_CALL_ERROR;

    public ApiCallException() {
        super(errorCode);
    }
}
