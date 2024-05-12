package com.kaispread.grabber.application.scrap;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@Getter
public enum DefaultHeader {
    DEFAULT(new HashMap<>());

    private final Map<String, String> map;

    DefaultHeader(Map<String, String> map) {
        this.map = map;
    }
}
