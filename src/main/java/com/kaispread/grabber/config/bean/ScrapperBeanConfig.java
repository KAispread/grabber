package com.kaispread.grabber.config.bean;

import static com.kaispread.grabber.application.scrap.ScrapperType.KAKAO_CORE;

import com.kaispread.grabber.application.scrap.Scrapper;
import com.kaispread.grabber.application.scrap.ScrapperFactory;
import com.kaispread.grabber.application.scrap.ScrapperType;
import com.kaispread.grabber.application.scrap.kakao.KakaoScrapper;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ScrapperBeanConfig {

    @Bean
    public ScrapperFactory scrapperFactory(WebClient webClient) {
        Map<ScrapperType, Scrapper> scrapperMap = Map.ofEntries(
            Map.entry(KAKAO_CORE, kakaoScrapper(webClient))
        );

        return new ScrapperFactory(scrapperMap);
    }

    @Bean
    public Scrapper kakaoScrapper(WebClient webClient) {
        return new KakaoScrapper(webClient);
    }
}
