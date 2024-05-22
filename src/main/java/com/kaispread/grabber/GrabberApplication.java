package com.kaispread.grabber;

import jakarta.annotation.PostConstruct;
import java.util.TimeZone;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GrabberApplication {

    public static void main(String[] args) {
        SpringApplication.run(GrabberApplication.class, args);
    }

    @PostConstruct
    public void init() {
        // Timezone setting
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }
}
