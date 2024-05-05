package com.kaispread.grabber.domain.company;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table("company")
public class Company {
    @Id
    private Long id;

    @NotNull private String name;
    @NotNull private String serviceName;
    @NotNull private String recruitmentUrl;

    @Builder
    public Company(String name, String serviceName, String recruitmentUrl) {
        this.name = name;
        this.serviceName = serviceName;
        this.recruitmentUrl = recruitmentUrl;
    }
}
