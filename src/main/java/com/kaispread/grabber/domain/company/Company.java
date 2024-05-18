package com.kaispread.grabber.domain.company;

import com.kaispread.grabber.application.scrap.ScrapperType;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table("company")
public class Company implements Persistable<String> {
    @Id
    private String id;

    @NotNull private String name;
    @NotNull private String serviceName;
    @NotNull private String recruitmentUrl;
    @NotNull private ScrapperType scrapperType;

    @CreatedDate private LocalDateTime createdDate;

    @Builder
    public Company(String id, String name, String serviceName, String recruitmentUrl, ScrapperType scrapperType) {
        this.id = id;
        this.name = name;
        this.serviceName = serviceName;
        this.recruitmentUrl = recruitmentUrl;
        this.scrapperType = scrapperType;
    }

    @Override
    public boolean isNew() {
        return createdDate == null;
    }
}
