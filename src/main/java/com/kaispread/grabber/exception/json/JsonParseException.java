package com.kaispread.grabber.exception.json;

import com.kaispread.grabber.exception.CustomException;
import com.kaispread.grabber.exception.ErrorCode;

public class JsonParseException extends CustomException {
    private static final ErrorCode errorCode = ErrorCode.PARSE_ERROR;

    public JsonParseException() {
        super(errorCode);
    }

    public JsonParseException(final String message) {
        super(errorCode, message);
    }
}
