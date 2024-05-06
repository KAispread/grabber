package com.kaispread.grabber.config.bean;

import static com.kaispread.grabber.application.scrap.ScrapperType.KAKAO_CORE;

import com.kaispread.grabber.application.api.ApiCaller;
import com.kaispread.grabber.application.scrap.Scrapper;
import com.kaispread.grabber.application.scrap.ScrapperFactory;
import com.kaispread.grabber.application.scrap.ScrapperType;
import com.kaispread.grabber.application.scrap.kakao.KakaoScrapper;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScrapperBeanConfig {

    @Bean
    public ScrapperFactory scrapperFactory(ApiCaller apiCaller) {
        Map<ScrapperType, Scrapper> scrapperMap = Map.ofEntries(
            Map.entry(KAKAO_CORE, kakaoScrapper(apiCaller))
        );

        return new ScrapperFactory(scrapperMap);
    }

    @Bean
    public Scrapper kakaoScrapper(ApiCaller apiCaller) {
        return new KakaoScrapper(apiCaller);
    }
}
