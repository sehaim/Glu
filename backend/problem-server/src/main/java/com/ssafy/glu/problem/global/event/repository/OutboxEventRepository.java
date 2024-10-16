package com.ssafy.glu.problem.global.event.repository;

import com.ssafy.glu.problem.global.event.document.OutboxEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OutboxEventRepository extends MongoRepository<OutboxEvent, String> {
    List<OutboxEvent> findAllByStatusIsNot(OutboxEvent.Status status);
    List<OutboxEvent> findAllByStatus(OutboxEvent.Status status);
}
