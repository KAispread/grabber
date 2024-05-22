package com.kaispread.grabber.domain.event;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
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
@Table("exception_event")
public class ExceptionEvent {
    @Id
    private Long id;

    @NotNull private String companyId;
    private String exception;
    private String description;

    @NotNull @CreatedDate
    private LocalDate createdDate;

    @Builder
    public ExceptionEvent(@NotNull String companyId, String exception, String description) {
        this.companyId = companyId;
        this.exception = exception;
        this.description = description;
    }
}
