package com.ssafy.glu.problem.global.event.service;

import com.ssafy.glu.problem.global.event.document.OutboxEvent;

import java.util.List;

public interface OutboxEventService {
    OutboxEvent saveOutbox(String topic, Object event);

    void sendEvent(OutboxEvent outboxEvent);

    void saveOutBoxAndSendEvent(String topic, Object event);

    List<OutboxEvent> getEventListNotSent();
}