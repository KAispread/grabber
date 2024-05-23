package com.kaispread.grabber.trigger.scheduler;

import static com.kaispread.grabber.utils.time.CurrentTimeGenerator.MONTH_DAY_TIME;

import com.kaispread.grabber.application.service.ScrapNotificationSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class ScrappingScheduler {

    private static final String SCHEDULE_START_LOG_FORMAT = "TIME : {} :: scheduled task started";
    private static final String SCHEDULE_END_LOG_FORMAT = "TIME : {} :: scheduled task completed successfully";
    private static final String SCHEDULE_ERROR_LOG_FORMAT = "TIME : {} :: scheduled task error, ERROR : {}";

    private final ScrapNotificationSender scrapNotificationSender;

    @Scheduled(cron = "0 0 18 * * *", zone = "Asia/Seoul")
    public void scrap() {
        log.info(SCHEDULE_START_LOG_FORMAT, MONTH_DAY_TIME.getCurrentTime());

        scrapNotificationSender.scrapAndSend()
            .doOnSuccess(success -> log.info(SCHEDULE_END_LOG_FORMAT, MONTH_DAY_TIME.getCurrentTime()))
            .doOnError(error -> log.warn(SCHEDULE_ERROR_LOG_FORMAT, MONTH_DAY_TIME.getCurrentTime(), error.getMessage()))
            .subscribe();
    }
}
