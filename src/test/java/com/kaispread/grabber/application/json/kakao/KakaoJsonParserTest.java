package com.kaispread.grabber.application.json.kakao;

import com.kaispread.grabber.application.dto.company.CompanyDto;
import com.kaispread.grabber.application.dto.scrap.ScrapJdDto;
import com.kaispread.grabber.base.support.IntegrationTestSupport;
import org.json.simple.JSONArray;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class KakaoJsonParserTest extends IntegrationTestSupport {

    @Autowired
    private KakaoJsonParser jsonParser;

    @DisplayName("카카오 공고 JSON 데이터를 파싱하여 ScrapJdDto로 변환할 수 있다.")
    @Test
    void parsing_success() {
        // given
        String scrapStr = getKakaoJdString();
        JSONArray jobList = (JSONArray) jsonParser.parseToJsonObject(scrapStr).get("jobList");

        // when
        Flux<ScrapJdDto> scrapJdDtoFlux = jsonParser.parseToJdDto(jobList, getCompanyDto())
            .doOnNext(System.out::println);

        // then
        StepVerifier.create(scrapJdDtoFlux)
            .expectNextMatches(ScrapJdDto::isValidDto)
            .expectNextMatches(ScrapJdDto::isValidDto)
            .expectNextMatches(ScrapJdDto::isValidDto)
            .as("# is invalid Dto ::")
            .verifyComplete();
    }

    private CompanyDto getCompanyDto() {
        return CompanyDto.builder()
            .id(1L)
            .companyName("카카오")
            .serviceName("카카오 코어")
            .build();
    }

    private String getKakaoJdString() {
        return """
            {
                "jobList": [
                    {
                        "realId": "P-13391",
                        "jobOfferId": 13391,
                        "jobOfferRegisterRequestId": 15828,
                        "privateFlag": false,
                        "pinFlag": false,
                        "closeFlag": false,
                        "jobOfferTitle": "인프라 시스템 엔지니어 (경력)",
                        "uptDate": "2024-04-03T14:39:10",
                        "regDate": "2023-09-26T15:29:33",
                        "endDate": null,
                        "recruitCount": 1,
                        "displayRecruitCount": "0",
                        "viewTypeCode": "BANNER",
                        "freeTextFlag": false,
                        "introduction": "우리 조직은 뛰어난 시스템 엔지니어들이 모여서 항상 새로운 기술에 대해서 고민하고, 우리가 보유한 기술 기반으로 서비스 개선을 하는 조직으로 카카오의 서비스를 위한 최적의 시스템을 구축할 수 있도록 기술적 사항을 종합적으로 검토 분석하여, 이에 적합한 시스템을 분석, 설계 구축, 운영하는 일을 합니다.",
                        "workContentDesc": "- 서버/스토리지 인프라 설계 및 구축<br/>- Linux 시스템 운영 및 관리<br/>- H/W 벤치마크 및 애플리케이션 성능 테스트<br/>- 시스템 자동화를 위한 아키텍처 설계 및 개발<br/>- HTTP, TCP/IP Protocol-Level 서비스 성능 분석 및 HTTP 콘텐츠 캐싱 서비스 운영",
                        "qualification": "- 우대사항에 기술된 경험과 함께 스스로 끊임 없이 역량을 키우고 주도성을 가지고 시스템을 개선하고자 하는 의지가 강하신분<br/><br/>**◆ 우대사항**<br/>- 포탈/게임사 경력이 있는 분<br/>- 대규모 인프라 운영 경험을 보유하신 분<br/>- 오픈소스를 이용한 플랫폼 구축 및 운영 경험을 보유하신 분<br/>- 클라우드 가상화 및 컨테이너 구축 및 운영 경험을 보유하신 분<br/>- 스크립트 및 프로그래밍 가능하신 분",
                        "jobOfferProcessDesc": "서류전형 &gt; 코딩테스트 &gt; 1차 인터뷰 &gt; 2차 인터뷰",
                        "krewComment": "어디가서도 경험하기 힘든 대규모의 인프라를 운영하고 있다는 것에 자부심을 느끼며 우수한 동료들과 함께 일하며 개인과 조직의 발전을 이뤄나가고 있습니다.<br/>여러분도 카카오의 시스템 엔지니어로서의 자부심을 느끼고 싶다면 저희와 함께 하시길 바랍니다.",
                        "useFlag": true,
                        "jobPart": "TECHNOLOGY",
                        "jobPartName": "테크",
                        "jobPartNameEn": "TECHNOLOGY",
                        "jobType": "TECHNOLOGY",
                        "jobTypeName": "테크",
                        "jobTypeNameEn": "Technology",
                        "companyCodeId": "dk",
                        "companyName": "카카오",
                        "companyNameEn": " Kakao Corp",
                        "locationCodeId": "PANGYO",
                        "locationName": "판교",
                        "locationNameEn": "Pangyo",
                        "employeeTypeCodeId": "0",
                        "employeeTypeName": "정규직",
                        "employeeTypeNameEn": "Full Time",
                        "workTypeCodeId": "CHOICE_TIME",
                        "workTypeName": "Kakao ON",
                        "workTypeNameEn": "",
                        "workTypeDesc": "<b>Kakao ON</b>:  해당 포지션은 월 총 근무시간 범위 내에서 크루 스스로 업무의 시작 및 종료시간을 설정하여 자율적으로 몰입하여 근무할 수 있는 “완전선택적근로제도\\"를 적용받습니다.<br><br> 카카오는 한 달 동안 열심히 달려온 크루의 소중한 휴식과 충전을 위해 매월 마지막 주 금요일을 리커버리데이로 운영합니다. 리커버리데이는 크루가 자유롭게 휴식과 충전, 업무리듬 재조정, 회고 및 다음 업무 계획 수립에 활용할 수 있습니다.<br><br> 카카오는 오피스를 주된 근무지로 하며, 각 조직별로 조직의 성과 창출과 업무 수행에 최적화된 근무 장소와 방식을 설정하고 있습니다.",
                        "skillSetList": [
                            {
                                "id": 15828,
                                "skillSetType": "System",
                                "skillSetName": "System",
                                "skillSetNameEn": "System",
                                "sortNo": 5
                            }
                        ],
                        "jobOfferAttachmentFileList": [],
                        "blindOpenRecruiting": false,
                        "blindOpenRecruitingTarget": false,
                        "noTargetMsg": null,
                        "seniorOpenRecruiting": false,
                        "notApplyFlag": false,
                        "selfIntroduceGuide": "",
                        "statusCode": "PROGRESS",
                        "mainCompanyJobOfferFlag": true,
                        "sortNo": 5,
                        "blank": false,
                        "kakaoJobOfferByRegisterRequestId": true,
                        "kakaoJobOfferByRealId": true
                    },
                    {
                        "realId": "P-13108",
                        "jobOfferId": 13108,
                        "jobOfferRegisterRequestId": 15544,
                        "privateFlag": false,
                        "pinFlag": false,
                        "closeFlag": false,
                        "jobOfferTitle": "데이터센터 운영 엔지니어 (경력)",
                        "uptDate": "2024-04-02T16:55:45",
                        "regDate": "2022-12-27T20:05:04",
                        "endDate": null,
                        "recruitCount": 3,
                        "displayRecruitCount": "0",
                        "viewTypeCode": "BANNER",
                        "freeTextFlag": false,
                        "introduction": "다양한 분야의 데이터센터 관련 전문 엔지니어들이 모여서 무중단 그리고 효율적인 데이터센터를 운영하는 조직입니다.",
                        "workContentDesc": "- 데이터센터 상면 계획 및 운영<br/>- 데이터센터 환경 관리 및 개선<br/>- 데이터센터 상면 구축<br/>- 데이터센터 계약 및 과금 데이터 관리<br/>- 랙 및 랙 악세서리 개발 적용",
                        "qualification": "- 2년 이상의 경력을 가진 분<br/>- 데이터센터 상면 관리 유경험자<br/>- 데이터센터 공조 및 전기에 대한 이해가 있는 분",
                        "jobOfferProcessDesc": "서류전형 &gt; 원격인터뷰 &gt; 1차인터뷰 &gt; 2차인터뷰",
                        "krewComment": "",
                        "useFlag": true,
                        "jobPart": "TECHNOLOGY",
                        "jobPartName": "테크",
                        "jobPartNameEn": "TECHNOLOGY",
                        "jobType": "TECHNOLOGY",
                        "jobTypeName": "테크",
                        "jobTypeNameEn": "Technology",
                        "companyCodeId": "dk",
                        "companyName": "카카오",
                        "companyNameEn": " Kakao Corp",
                        "locationCodeId": "PANGYO",
                        "locationName": "판교",
                        "locationNameEn": "Pangyo",
                        "employeeTypeCodeId": "0",
                        "employeeTypeName": "정규직",
                        "employeeTypeNameEn": "Full Time",
                        "workTypeCodeId": "CHOICE_TIME",
                        "workTypeName": "Kakao ON",
                        "workTypeNameEn": "",
                        "workTypeDesc": "<b>Kakao ON</b>:  해당 포지션은 월 총 근무시간 범위 내에서 크루 스스로 업무의 시작 및 종료시간을 설정하여 자율적으로 몰입하여 근무할 수 있는 “완전선택적근로제도\\"를 적용받습니다.<br><br> 카카오는 한 달 동안 열심히 달려온 크루의 소중한 휴식과 충전을 위해 매월 마지막 주 금요일을 리커버리데이로 운영합니다. 리커버리데이는 크루가 자유롭게 휴식과 충전, 업무리듬 재조정, 회고 및 다음 업무 계획 수립에 활용할 수 있습니다.<br><br> 카카오는 오피스를 주된 근무지로 하며, 각 조직별로 조직의 성과 창출과 업무 수행에 최적화된 근무 장소와 방식을 설정하고 있습니다.",
                        "skillSetList": [
                            {
                                "id": 15544,
                                "skillSetType": "etc",
                                "skillSetName": "기타",
                                "skillSetNameEn": "etc",
                                "sortNo": 15
                            }
                        ],
                        "jobOfferAttachmentFileList": [],
                        "blindOpenRecruiting": false,
                        "blindOpenRecruitingTarget": false,
                        "noTargetMsg": null,
                        "seniorOpenRecruiting": false,
                        "notApplyFlag": false,
                        "selfIntroduceGuide": "",
                        "statusCode": "PROGRESS",
                        "mainCompanyJobOfferFlag": true,
                        "sortNo": 15,
                        "blank": false,
                        "kakaoJobOfferByRegisterRequestId": true,
                        "kakaoJobOfferByRealId": true
                    },
                    {
                        "realId": "P-13551",
                        "jobOfferId": 13551,
                        "jobOfferRegisterRequestId": 15988,
                        "privateFlag": false,
                        "pinFlag": false,
                        "closeFlag": false,
                        "jobOfferTitle": "인프라 서비스 플랫폼 개발자 (경력)",
                        "uptDate": "2024-04-02T14:05:10",
                        "regDate": "2024-03-06T13:49:42",
                        "endDate": null,
                        "recruitCount": 0,
                        "displayRecruitCount": "0",
                        "viewTypeCode": "BANNER",
                        "freeTextFlag": false,
                        "introduction": "우리 조직은 카카오 서비스의 인프라 시스템과 트래픽을 개발 및 관리합니다.<br/>최고 수준의 인프라 개발자들과 함께 국내 최대 규모의 리소스를 대상으로 플랫폼을 개발하고, 시스템의 성능과 안정성을 항상 개선하고자 노력합니다.<br/>또한, 우리는 카카오 서비스를 원활하게 운영하기 위해 항상 최신 기술과 도구를 적용하고, 시스템의 성능과 안정성을 향상시키는 데 주력하고 있습니다.<br/>우리의 목표는 카카오 서비스의 성공을 위해 최고 수준의 플랫폼을 개발/구축하고 운영하는 것입니다.",
                        "workContentDesc": "- 전사 자산관리 CMDB 서비스 개발/운영/유지보수<br/>- 전사 유휴시스템 관리 서비스 개발/운영/유지보수<br/>- 인프라 서비스 통합/자동화를 위한 플랫폼 개발/운영/유지보수",
                        "qualification": "[ 공통 필수 사항 ]<br/>- 개발 경력 만 5년 이상<br/>- Git, JIRA, Jenkins 등의 업무 도구에 익숙하신 분<br/>- 리눅스 시스템, 네트워크에 대한 기본적인 이해 및 경험<br/>- Docker, Kubernetes 등 컨테이너 오케스트레이션에 대한 이해 및 경험<br/><br/>[ Backend 개발자 지원자격 ]<br/>- Linux 플랫폼에서 웹 서비스 개발/운영<br/>- Java/Spring 기반 웹 서비스 개발 경력<br/>- Spring Boot 기반 웹 애플리케이션 개발 경험<br/>- 리눅스 시스템, 네트워크에 대한 기본적인 이해 및 경험<br/><br/>[ Frontend 개발자 지원자격 ]<br/>- ReactJS 기반 프론트엔드 개발 경력<br/>- Javascript, HTML, CSS에 익숙하신 분<br/><br/>**◆ 우대사항**<br/>[ 공통 사항 ]<br/>- 소프트웨어의 품질 향상을 지속적으로 고민하시는 분<br/>- 능동적인 기능 제안 및 개선 의지가 강하신 분<br/>- 빌드/테스트/배포 자동화 경험이 있으신 분<br/><br/>[ Backend 개발자 우대사항 ]<br/>- Spring Batch를 통한 작업 경험이 있으신 분<br/>- SQL에 대한 이해도가 높으신 분<br/>- PostgreSQL, MySQL 등 다양한 RDBMS 에 대한 경험이 있으신 분<br/>- Redis, Kafka, Elasticsearch 등 오픈소스 플랫폼에 대한 이해와 경험이 있으신 분<br/>- 대용량 트래픽의 인프라 서비스에 대한 개발 및 운영 경험이 있으신 분<br/><br/>[ Frontend 개발자 우대사항 ]<br/>- TypeScript에 익숙하신 분<br/>- Next.js에 익숙하신 분<br/>- 사용자 경험을 중요하게 여기시는 분",
                        "jobOfferProcessDesc": "서류전형 &gt; 코딩테스트 &gt; 사전 인터뷰 &gt; 1차 인터뷰 &gt; 2차 인터뷰",
                        "krewComment": "우리 조직은 최신 기술과 도구를 적용하며, 새로운 시도를 위한 기술 스터디를 적극 권장합니다.<br/>열정 넘치는 동료들과 활기찬 문화 속에서 성장하고 다양한 도전을 해보고 싶으신 분이 있으시다면, 저희와 함께 하시길 바랍니다.",
                        "useFlag": true,
                        "jobPart": "TECHNOLOGY",
                        "jobPartName": "테크",
                        "jobPartNameEn": "TECHNOLOGY",
                        "jobType": "TECHNOLOGY",
                        "jobTypeName": "테크",
                        "jobTypeNameEn": "Technology",
                        "companyCodeId": "dk",
                        "companyName": "카카오",
                        "companyNameEn": " Kakao Corp",
                        "locationCodeId": "PANGYO",
                        "locationName": "판교",
                        "locationNameEn": "Pangyo",
                        "employeeTypeCodeId": "0",
                        "employeeTypeName": "정규직",
                        "employeeTypeNameEn": "Full Time",
                        "workTypeCodeId": "CHOICE_TIME",
                        "workTypeName": "Kakao ON",
                        "workTypeNameEn": "",
                        "workTypeDesc": "<b>Kakao ON</b>:  해당 포지션은 월 총 근무시간 범위 내에서 크루 스스로 업무의 시작 및 종료시간을 설정하여 자율적으로 몰입하여 근무할 수 있는 “완전선택적근로제도\\"를 적용받습니다.<br><br> 카카오는 한 달 동안 열심히 달려온 크루의 소중한 휴식과 충전을 위해 매월 마지막 주 금요일을 리커버리데이로 운영합니다. 리커버리데이는 크루가 자유롭게 휴식과 충전, 업무리듬 재조정, 회고 및 다음 업무 계획 수립에 활용할 수 있습니다.<br><br> 카카오는 오피스를 주된 근무지로 하며, 각 조직별로 조직의 성과 창출과 업무 수행에 최적화된 근무 장소와 방식을 설정하고 있습니다.",
                        "skillSetList": [
                            {
                                "id": 15988,
                                "skillSetType": "Web_front",
                                "skillSetName": "Web front",
                                "skillSetNameEn": "Web front",
                                "sortNo": 4
                            },
                            {
                                "id": 15988,
                                "skillSetType": "Server",
                                "skillSetName": "Server",
                                "skillSetNameEn": "Server",
                                "sortNo": 8
                            }
                        ],
                        "jobOfferAttachmentFileList": [],
                        "blindOpenRecruiting": false,
                        "blindOpenRecruitingTarget": false,
                        "noTargetMsg": null,
                        "seniorOpenRecruiting": false,
                        "notApplyFlag": false,
                        "selfIntroduceGuide": "",
                        "statusCode": "PROGRESS",
                        "mainCompanyJobOfferFlag": true,
                        "sortNo": 8,
                        "blank": false,
                        "kakaoJobOfferByRegisterRequestId": true,
                        "kakaoJobOfferByRealId": true
                    }
                ],
                "jobTypeCountDtoList": [
                    {
                        "jobType": "TECHNOLOGY",
                        "jobCount": 3
                    },
                    {
                        "jobType": "STAFF",
                        "jobCount": 10
                    },
                    {
                        "jobType": "BUSINESS_SERVICES",
                        "jobCount": 6
                    },
                    {
                        "jobType": "DESIGN/BRAND_MARKETING",
                        "jobCount": 2
                    }
                ],
                "totalJobCount": 3,
                "totalPage": 1
            }
            """;
    }
}