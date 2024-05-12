package com.kaispread.grabber.domain.jd;

import static com.kaispread.grabber.domain.jd.Position.APP;
import static com.kaispread.grabber.domain.jd.Position.BACKEND;
import static com.kaispread.grabber.domain.jd.Position.DEVOPS;
import static com.kaispread.grabber.domain.jd.Position.FRONTEND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class PositionTest {

    @DisplayName("백엔드 공고명을 통해 Position.BACKEND 인스턴스를 반환받을 수 있다.")
    @ValueSource(strings = {
        "[Tech] 업무시스템 서버 개발자",
        "[Tech] 상답시스템(챗봇) 백엔드 개발자",
        "백엔드 엔지니어 (Nest.js / 5년 이상)",
        "Cloud Back-End 개발자",
        "서버/백엔드 개발자"
    })
    @ParameterizedTest
    void get_backend(String jobTitle) {
        // given
        Position result = Position.getInstance(jobTitle);

        // when & then
        assertThat(result).isEqualTo(BACKEND);
    }

    @DisplayName("프론트엔드 공고명을 통해 Position.FRONT 인스턴스를 반환받을 수 있다.")
    @ValueSource(strings = {
        "[Tech] 운영도구개발팀 웹프론트엔드 개발자",
        "Frontend (React / Angular) 개발자(5년 이상)",
        "Frontend Engineer(5년 이상)",
        "프론트엔드 개발자 (3년 이상)",
        "Software Engineer (Front-end) - 3년 이상",
        "Web 프론트 개발자"
    })
    @ParameterizedTest
    void get_frontend(String jobTitle) {
        // given
        Position result = Position.getInstance(jobTitle);

        // when & then
        assertThat(result).isEqualTo(FRONTEND);
    }

    @DisplayName("DevOps 공고명을 통해 Position.DEVOPS 인스턴스를 반환받을 수 있다.")
    @ValueSource(strings = {
        "아틀라시안 & 데브옵스 솔루션 엔지니어",
        "AWS 인프라 운영 및 보안 담당자",
        "Microsoft Azure Infra Engineer",
        "DevOps 엔지니어",
        "클라우드 아키텍트",
        "Infra / DevOps 엔지니어",
        "[300억↑투자] DevSecOps (5년 이상)"
    })
    @ParameterizedTest
    void get_devops(String jobTitle) {
        // given
        Position result = Position.getInstance(jobTitle);

        // when & then
        assertThat(result).isEqualTo(DEVOPS);
    }

    @DisplayName("앱 개발자 공고명을 통해 Position.APP 인스턴스를 반환받을 수 있다.")
    @ValueSource(strings = {
        "Software Engineer (Android) - 3년 이상",
        "IOS 개발자(5~10년)",
        "PayVerse 개발팀 UI(APP)개발자",
        "Android Application Engineer (IVI App Store)",
        "[100억↑투자] React Native Developer",
        "안드로이드 개발자",
        "Flutter 개발자"
    })
    @ParameterizedTest
    void get_app(String jobTitle) {
        // given
        Position result = Position.getInstance(jobTitle);

        // when & then
        assertThat(result).isEqualTo(APP);
    }

}