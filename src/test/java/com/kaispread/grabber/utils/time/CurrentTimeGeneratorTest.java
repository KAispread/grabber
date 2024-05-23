package com.kaispread.grabber.utils.time;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CurrentTimeGeneratorTest {

    @DisplayName("현재 시간을 N월 M일 xx:xx 형태로 출력할 수 있다.")
    @Test
    void getTime() {
        // when
        String time = CurrentTimeGenerator.MONTH_DAY_TIME.getCurrentTime();

        // then
        System.out.println(time);
        assertThat(time.length()).isGreaterThan(11);
    }
}