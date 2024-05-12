package com.kaispread.grabber.application.scrap;

import com.kaispread.grabber.application.dto.company.CompanyDto;
import com.kaispread.grabber.application.dto.scrap.ScrapJdDto;
import java.util.Map;
import reactor.core.publisher.Flux;

public interface JobDescriptionScrapper extends Scrapper {
    Flux<ScrapJdDto> scrap(CompanyDto companyDto, Map<String, String> header);
}
