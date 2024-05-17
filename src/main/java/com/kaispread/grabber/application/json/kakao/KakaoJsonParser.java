package com.kaispread.grabber.application.json.kakao;

import com.kaispread.grabber.application.dto.company.CompanyDto;
import com.kaispread.grabber.application.dto.scrap.ScrapJdDto;
import com.kaispread.grabber.application.json.CustomJsonParser;
import com.kaispread.grabber.domain.jd.Position;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class KakaoJsonParser extends CustomJsonParser {

    private static final String JOB_ID = "realId";
    private static final String JOB_TITLE = "jobOfferTitle";
    private static final String INTRODUCTION = "workContentDesc";
    private static final String QUALIFICATION = "qualification";
    private static final String PROCESS = "jobOfferProcessDesc";
    private static final String LOCATION = "locationName";

    private static final String URL_FORMAT = "https://careers.kakao.com/jobs/%s";

    public KakaoJsonParser(JSONParser jsonParser) {
        super(jsonParser);
    }

    @Override
    public Flux<ScrapJdDto> parseToJdDto(final JSONArray jsonArray, final CompanyDto companyDto) {
        return Flux.fromArray(jsonArray.toArray())
            .map(this::parseToJsonObject)
            .flatMap(jsonObject -> Mono.just(parseToScrapJdDto(jsonObject, companyDto)));
    }

    private ScrapJdDto parseToScrapJdDto(final JSONObject jsonObject, final CompanyDto companyDto) {
        String jobId = getAndConvertToString(jsonObject, JOB_ID);
        String jobTitle = getAndConvertToString(jsonObject, JOB_TITLE);

        return ScrapJdDto.builder()
            .companyId(companyDto.id())
            .serviceName(companyDto.serviceName())
            .companyName(companyDto.companyName())
            .jdId(jobId)
            .jdUrl(String.format(URL_FORMAT, jobId))
            .jdTitle(getAndConvertToString(jsonObject, JOB_TITLE))
            .jobProcess(getAndConvertToString(jsonObject, PROCESS))
            .position(Position.getInstance(jobTitle))
            .qualification(getAndConvertToString(jsonObject, QUALIFICATION))
            .introduction(getAndConvertToString(jsonObject, INTRODUCTION))
            .location(getAndConvertToString(jsonObject, LOCATION))
            .build();
    }

    private String getAndConvertToString(final JSONObject jsonObject, final String key) {
        return String.valueOf(jsonObject.get(key));
    }
}
