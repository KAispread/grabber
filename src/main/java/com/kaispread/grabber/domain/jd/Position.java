package com.kaispread.grabber.domain.jd;

import java.util.Arrays;
import java.util.List;
import lombok.Getter;

@Getter
public enum Position {
    FRONTEND (List.of("frontend", "front-end", "front_end", "프론트엔드", "프론트앤드", "프런트엔드", "프론트")),
    BACKEND (List.of("backend", "back-end", "back_end", "백엔드", "백앤드", "서버", "server")),
    DEVOPS (List.of("devops", "devsecops", "dev-ops", "데브옵스", "infra", "infrastructure", "인프라", "AWS", "GCP", "Azure", "클라우드", "cloud")),
    APP(List.of("안드로이드", "android", "ios", "app", "앱", "react native", "flutter")),
    ETC (null);

    private final List<String> names;

    Position(List<String> names) {
        this.names = names;
    }

    public static Position getInstance(final String jobTitle) {
        String lowerCaseJobTitle = jobTitle.toLowerCase();
        return Arrays.stream(Position.values())
            .filter(position -> position.getNames() != null)
            .filter(position -> position.getNames().stream().anyMatch(lowerCaseJobTitle::contains))
            .findFirst()
            .orElse(ETC);
    }
}
