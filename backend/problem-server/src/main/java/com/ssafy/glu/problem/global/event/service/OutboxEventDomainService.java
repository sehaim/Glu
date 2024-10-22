package com.ssafy.glu.problem.global.event.service;

import com.ssafy.glu.problem.global.event.document.OutboxEvent;
import com.ssafy.glu.problem.global.event.repository.OutboxEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OutboxEventDomainService implements OutboxEventService{
    private final OutboxEventRepository outboxEventRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public OutboxEvent saveOutbox(String topic, Object event) {
        OutboxEvent outboxEvent = OutboxEvent.builder()
                .topic(topic)
                .payload(event)
                .build();
        return outboxEventRepository.save(outboxEvent);
    }

    @Override
    public void sendEvent(OutboxEvent outboxEvent) {
        try {
            kafkaTemplate.send(outboxEvent.getTopic(), outboxEvent.getPayload());
            outboxEvent.success();
            log.info("이벤트 발행에 성공하였습니다.");
        } catch (Exception e) {
            outboxEvent.fail();
            log.info("이벤트 발행에 실패하였습니다.");
        }
        outboxEventRepository.save(outboxEvent);
    }

    @Override
    public void saveOutBoxAndSendEvent(String topic, Object event) {
        sendEvent(saveOutbox(topic, event));
    }

    @Override
    public List<OutboxEvent> getEventListNotSent() {
        return outboxEventRepository.findAllByStatus(OutboxEvent.Status.FAILED);
    }
}