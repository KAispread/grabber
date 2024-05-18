package com.kaispread.grabber.domain.event;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ExceptionEventRepository extends ReactiveCrudRepository<ExceptionEvent, Long>  {
}
