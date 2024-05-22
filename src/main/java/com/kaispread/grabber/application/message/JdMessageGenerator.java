package com.kaispread.grabber.application.message;

import com.kaispread.grabber.application.dto.scrap.ScrapJdDto;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/*
For Example
[5월 21일 신규 채용 공고] (Daily)
 - TOSS -
[토스] 토스 Data 공개채용_Data Analyst [대출사업]
https://toss.im/career/job-detail?gh_jid=5988626003

 - NAVER LABS -
[네이버랩스][ALIKE Solution] Computer Vision / 3D Recon. Research Engineer
https://recruit.navercorp.com/rcrt/view.do?annoId=30002082&lang=ko
[네이버랩스][ALIKE Solution] Backend engineer
https://recruit.navercorp.com/rcrt/view.do?annoId=30002082&lang=ko
*/
public abstract class JdMessageGenerator {

    // No Event Message
    protected static final String NO_EVENT = "오늘은 새로운 공고를 찾지 못했습니다";

    // Format
    private static final String HEAD_LINE = "[%s 신규 채용 공고] (%s)";
    private static final String TITLE_SERVICE_NAME_FORMAT = "- %s - \n%s";
    private static final String TITLE_JOB_DESCRIPTION = "[%s] %s \n%s";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("M월 d일");

    /*
    * 메시지 첫 줄 문구 생성
    * ex) [5월 21일 신규 채용 공고] (Daily & Backend & Frontend ..)
    * */
    protected String getHeadLine(final String channelName) {
        String todayDate = LocalDate.now().format(DATE_FORMAT);
        return String.format(HEAD_LINE, todayDate, channelName);
    }

    /*
    * 서비스별 공고 문구 생성
    * - TOSS -
    * [토스] 토스 Data 공개채용_Data Analyst [대출사업]
    * https://toss.im/career/job-detail?gh_jid=5988626003
    * */
    protected String getMessageBody(final String serviceName, final List<ScrapJdDto> scrapJdDtoList) {
        String jobDetails = scrapJdDtoList.stream()
            .map(jd -> String.format(TITLE_JOB_DESCRIPTION,
                jd.companyName(),
                jd.jdTitle(),
                jd.jdUrl()))
            .collect(Collectors.joining("\n"));
        return String.format(TITLE_SERVICE_NAME_FORMAT, serviceName, jobDetails);
    }
}
