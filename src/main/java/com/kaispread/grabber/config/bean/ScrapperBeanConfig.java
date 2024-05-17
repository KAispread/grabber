package com.kaispread.grabber.config.bean;

import static com.kaispread.grabber.application.scrap.ScrapperType.KAKAO_CORE;

import com.kaispread.grabber.application.api.ApiCaller;
import com.kaispread.grabber.application.json.kakao.KakaoJsonParser;
import com.kaispread.grabber.application.scrap.JobDescriptionScrapper;
import com.kaispread.grabber.application.scrap.Scrapper;
import com.kaispread.grabber.application.scrap.ScrapperFactory;
import com.kaispread.grabber.application.scrap.ScrapperType;
import com.kaispread.grabber.application.scrap.kakao.KakaoScrapper;
import java.util.Map;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScrapperBeanConfig {

    @Bean
    public ScrapperFactory scrapperFactory(@Qualifier("kakaoScrapper") JobDescriptionScrapper kakaoScrapper) {
        Map<ScrapperType, Scrapper> scrapperMap = Map.ofEntries(
            Map.entry(KAKAO_CORE, kakaoScrapper)
        );

        return new ScrapperFactory(scrapperMap);
    }

    @Bean
    public JobDescriptionScrapper kakaoScrapper(ApiCaller apiCaller, KakaoJsonParser jsonParser) {
        return new KakaoScrapper(apiCaller, jsonParser);
    }
}
