package com.kaispread.grabber.exception.convert;

import com.kaispread.grabber.exception.CustomException;
import com.kaispread.grabber.exception.ErrorCode;

public class ConvertJobDescriptionException extends CustomException {
    private static final ErrorCode errorCode = ErrorCode.CONVERT_JOB_DESCRIPTION;

    public ConvertJobDescriptionException() {
        super(errorCode);
    }
}
