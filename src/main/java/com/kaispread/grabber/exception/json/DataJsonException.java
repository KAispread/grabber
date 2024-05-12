package com.kaispread.grabber.exception.json;

import com.kaispread.grabber.application.dto.company.CompanyDto;
import com.kaispread.grabber.exception.ErrorCode;
import com.kaispread.grabber.exception.ContainsCompanyDataException;

public class DataJsonException extends ContainsCompanyDataException {
    private static final ErrorCode errorCode = ErrorCode.PARSE_ERROR;

    public DataJsonException(CompanyDto companyData) {
        super(errorCode, companyData);
    }
}
