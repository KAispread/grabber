package com.kaispread.grabber.domain.event;

import java.time.LocalDate;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ExceptionEventRepository extends ReactiveCrudRepository<ExceptionEvent, Long>  {
    Flux<ExceptionEvent> findByCreatedDate(LocalDate createdDate);
}
