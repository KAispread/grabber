package com.kaispread.grabber.application.scrap.kakao;


import com.kaispread.grabber.application.api.ApiCaller;
import com.kaispread.grabber.application.dto.company.CompanyDto;
import com.kaispread.grabber.application.dto.scrap.ScrapJdDto;
import com.kaispread.grabber.application.scrap.JobDescriptionScrapper;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class KakaoScrapper implements JobDescriptionScrapper {

    private final ApiCaller apiCaller;

    @Override
    public Mono<List<ScrapJdDto>> scrap(final CompanyDto companyDto, final Map<String, String> header) {
        return null;
    }
}
