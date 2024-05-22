package com.kaispread.grabber.application.slack;

import lombok.Getter;

@Getter
public enum SlackChannel {
    DAILY_ALL("Daily"),
    BACKEND("Backend"),
    FRONTEND("Frontend")
    ;

    private final String channelName;

    SlackChannel(String channelName) {
        this.channelName = channelName;
    }
}
