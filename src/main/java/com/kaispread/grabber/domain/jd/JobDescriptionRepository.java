package com.kaispread.grabber.domain.jd;

import java.util.Collection;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface JobDescriptionRepository extends ReactiveCrudRepository<JobDescription, Long> {

    @Query("""
           SELECT job_id FROM job_description
           WHERE company_id = :companyId AND job_id IN (:jobIds)
           """)
    Flux<String> findByCompanyIdAndJobId(@Param("companyId") String companyId, @Param("jobIds") Collection<String> jobIds);
}
