package com.kaispread.grabber.domain.jd;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table("job_description")
public class JobDescription {
    @Id
    private Long id;

    @NotNull private Long companyId;
    @NotNull private String url;
    @NotNull private String jobTitle;
    @NotNull private Position jobPosition;
    @NotNull private boolean closeFlag;

    private String jobProcess;
    private String requiredSkill;
    private String qualification;
    private String location;

    @NotNull @CreatedDate
    private LocalDateTime createdDate;

    @Builder
    public JobDescription(Long companyId, String url, String jobTitle, Position jobPosition,
                          String jobProcess, String requiredSkill, String qualification,
                          String location, boolean closeFlag) {
        this.companyId = companyId;
        this.url = url;
        this.jobTitle = jobTitle;
        this.jobPosition = jobPosition;
        this.jobProcess = jobProcess;
        this.requiredSkill = requiredSkill;
        this.qualification = qualification;
        this.location = location;
        this.closeFlag = closeFlag;
    }
}
