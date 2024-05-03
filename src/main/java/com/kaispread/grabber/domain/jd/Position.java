package com.kaispread.grabber.domain.jd;

import java.util.List;
import lombok.Getter;

@Getter
public enum Position {
    BACKEND (List.of("backend")),
    FRONTEND (List.of("frontend")),
    DEVOPS (List.of("devops")),
    DATA (List.of("data_engineer")),
    ETC (null);

    private final List<String> names;

    Position(List<String> names) {
        this.names = names;
    }
}
