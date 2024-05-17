package com.kaispread.grabber.exception;

import com.kaispread.grabber.application.dto.company.CompanyDto;

public class ContainsCompanyDataException extends CustomException {

    private CompanyDto companyData;

    public ContainsCompanyDataException(ErrorCode errorCode, CompanyDto companyData) {
        super(errorCode);
        this.companyData = companyData;
    }

    public CompanyDto getCompanyData() {
        return companyData;
    }
}
