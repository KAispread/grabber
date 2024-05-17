package com.kaispread.grabber.exception.external;

import com.kaispread.grabber.application.dto.company.CompanyDto;
import com.kaispread.grabber.exception.ErrorCode;
import com.kaispread.grabber.exception.ContainsCompanyDataException;

public class DataApiCallException extends ContainsCompanyDataException {
    private static final ErrorCode errorCode = ErrorCode.API_CALL_ERROR;

    public DataApiCallException(CompanyDto companyData) {
        super(errorCode, companyData);
    }
}
