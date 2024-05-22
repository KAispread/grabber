package com.kaispread.grabber.application.message;

import static org.assertj.core.api.Assertions.assertThat;

import com.kaispread.grabber.application.dto.scrap.ScrapJdDto;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JdMessageGeneratorTest {

    @DisplayName("첫 줄 문구를 가져올 수 있다.")
    @Test
    void getHeadLine() {
        // given
        MockJdMessageGenerator messageGenerator = new MockJdMessageGenerator();

        // when
        String result = messageGenerator.getHeadLine("Daily");

        // then
        System.out.println(result);
        assertThat(result).isNotBlank();
    }

    @DisplayName("서비스별 공고 문구를 생성할 수 있다.")
    @Test
    void getMessageBody() {
        // given
        MockJdMessageGenerator messageGenerator = new MockJdMessageGenerator();
        String serviceName = "Toss";
        List<ScrapJdDto> serviceScrapList = getServiceScrapList();

        // when
        String messageBody = messageGenerator.getMessageBody(serviceName, serviceScrapList);

        // then
        System.out.println(messageBody);
        assertThat(messageBody).isNotBlank();
    }

    private static List<ScrapJdDto> getServiceScrapList() {
        return List.of(ScrapJdDto.builder()
                .companyId("A001")
                .companyName("Toss")
                .jdTitle("백엔드 엔지니어")
                .jdUrl("mock.url.com/1")
                .jdId("JE001")
                .build(),
            ScrapJdDto.builder()
                .companyId("A001")
                .companyName("Toss")
                .jdTitle("프론트엔드 엔지니어")
                .jdUrl("mock.url.com/2")
                .jdId("JE002")
                .build());
    }

    static class MockJdMessageGenerator extends JdMessageGenerator {
    }
}