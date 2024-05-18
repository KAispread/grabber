package com.kaispread.grabber.application.scrap.kakao;


import com.kaispread.grabber.application.api.ApiCaller;
import com.kaispread.grabber.application.dto.company.CompanyDto;
import com.kaispread.grabber.application.dto.scrap.ScrapJdDto;
import com.kaispread.grabber.application.json.kakao.KakaoJsonParser;
import com.kaispread.grabber.application.scrap.JobDescriptionScrapper;
import com.kaispread.grabber.exception.external.DataApiCallException;
import com.kaispread.grabber.exception.json.DataJsonException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class KakaoScrapper implements JobDescriptionScrapper {

    private static final String JOB_LIST_KEY = "jobList";

    private final ApiCaller apiCaller;
    private final KakaoJsonParser parser;

    @Override
    public Flux<ScrapJdDto> scrap(final CompanyDto companyDto, final Map<String, String> header) {
        return apiCaller.get(companyDto.uri(), String.class)
            .onErrorResume(error -> Mono.error(new DataApiCallException(companyDto)))
            .retry(3)
            .flatMap(scrapStr -> Mono.just(parser.parseToJsonObject(scrapStr)))
            .flatMap(jsonObject -> Mono.just(parser.parseToJsonArray(jsonObject, JOB_LIST_KEY)))
            .flatMapMany(jsonArray -> parser.parseToJdDto(jsonArray, companyDto))
            .onErrorResume(error -> !(error instanceof DataApiCallException),
                            data -> Mono.error(new DataJsonException(companyDto)));
    }
}
