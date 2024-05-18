package com.kaispread.grabber.presentation;

import com.kaispread.grabber.application.scrap.core.MainScrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RequestMapping("/v1/scrap")
@RestController
public class ScrapperController {
    private final MainScrapper mainScrapper;

    @PostMapping
    public Mono<Void> post() {
        return mainScrapper.runScrapping()
                           .then();
    }
}
