package com.kaispread.grabber.presentation;

import com.kaispread.grabber.application.service.ScrapNotificationSender;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RequestMapping("/v1/scrap")
@RestController
public class ScrapperController {
    private final ScrapNotificationSender scrapNotificationSender;

    @PostMapping
    public Mono<Void> post() {
        return scrapNotificationSender.scrapAndSend();
    }
}
