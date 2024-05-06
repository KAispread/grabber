package com.kaispread.grabber.application.service;

import com.kaispread.grabber.application.dto.scrap.ScrapJdDto;
import com.kaispread.grabber.application.scrap.ScrapperFactory;
import com.kaispread.grabber.domain.company.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Service
public class JdScrappingService {

    private ScrapperFactory scrapperFactory;
    private CompanyRepository companyRepository;

    public Flux<ScrapJdDto> runJdScrapping() {
        return null;
    }
}
