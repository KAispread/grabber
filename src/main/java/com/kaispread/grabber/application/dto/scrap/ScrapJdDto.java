package com.kaispread.grabber.application.dto.scrap;

import com.kaispread.grabber.application.dto.error.ScrapError;
import com.kaispread.grabber.domain.jd.Position;
import lombok.Builder;

@Builder
public record ScrapJdDto (
    String serviceName,
    String companyName,
    String jdId,
    String jdUrl,
    String jdTitle,
    Position position,
    String jobProcess,
    String requiredSkill,
    String qualification,
    String location,

    ScrapError error
) {
    public boolean isError() {
        return error != null;
    }
}
