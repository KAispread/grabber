CREATE TABLE IF NOT EXISTS `company`
(
    `id`           integer(10)              NOT NULL AUTO_INCREMENT,
    `name`         varchar(255)             NOT NULL COMMENT '회사명',
    `service_name` varchar(255)             NOT NULL COMMENT '서비스명',
    `recruitment_url` varchar(255)          NOT NULL,
    `scrapper_type`   varchar(100)          NOT NULL,

    PRIMARY KEY (id),
    UNIQUE KEY (recruitment_url)
);

CREATE TABLE IF NOT EXISTS `job_description`
(
    `id`             int                    NOT NULL AUTO_INCREMENT,
    `company_id`     int                    NOT NULL,

    `job_id`         varchar(100)           NOT NULL COMMENT '각 채용 공고에 할당된 고유 ID',
    `url`            varchar(255)           NOT NULL COMMENT 'JD URL',
    `job_title`      varchar(255)           NOT NULL COMMENT '공고명',
    `job_position`   varchar(50)            NOT NULL COMMENT '포지션',
    `job_process`    varchar(100)           COMMENT '채용 절차',
    `introduction`   TEXT                   COMMENT '직무 소개',
    `required_skill` varchar(255)           COMMENT '필요 스킬',
    `qualification`  TEXT                   COMMENT '자격/우대 사항',
    `location`       varchar(100)           COMMENT '위치',
    `close_flag`     tinyint                NOT NULL COMMENT '마감 여부',
    `created_date`   DATETIME DEFAULT NOW() NOT NULL COMMENT '생성일',

    PRIMARY KEY (id)
);
CREATE INDEX idx_job_id_company_id ON `job_description`(job_id, company_id);
