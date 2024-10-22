package com.ssafy.glu.problem.global.event.service;

import com.ssafy.glu.problem.global.event.document.OutboxEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class OutboxEventProcessor {
    private final OutboxEventService outboxEventService;

    @Scheduled(fixedRate = 5000) // Retry every 5 seconds
    public void processOutboxEvents() {
        List<OutboxEvent> pendingEvents = outboxEventService.getEventListNotSent();

        for (OutboxEvent outboxEvent : pendingEvents) {
            log.info("이벤트 발행 재시도 : {}", outboxEvent);
            outboxEventService.sendEvent(outboxEvent);
        }
    }
}