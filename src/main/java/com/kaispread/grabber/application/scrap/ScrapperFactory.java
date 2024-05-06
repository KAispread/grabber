package com.kaispread.grabber.application.scrap;

import java.util.Map;

public class ScrapperFactory {

    private final Map<ScrapperType, Scrapper> scrapperMap;

    public ScrapperFactory(Map<ScrapperType, Scrapper> scrapperMap) {
        this.scrapperMap = scrapperMap;
    }

    public JobDescriptionScrapper getJdScrapper(final ScrapperType type) {
        return (JobDescriptionScrapper) scrapperMap.get(type);
    }
}
