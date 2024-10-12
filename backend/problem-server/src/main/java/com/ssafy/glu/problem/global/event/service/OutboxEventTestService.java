package com.ssafy.glu.problem.global.event.service;

import com.ssafy.glu.problem.global.event.document.OutboxEvent;
import com.ssafy.glu.problem.global.event.repository.OutboxEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
 * 30% 확률로 이벤트 발행에 실행하게 하기 위한 OutboxEvent 테스트 클래스
 * 실제 운영환경에서 사용하지 말 것!!!
 */
//@Service
@RequiredArgsConstructor
@Slf4j
public class OutboxEventTestService implements OutboxEventService{
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
        Random random = new Random();

        int randNum = random.nextInt(10);

        // 30% 확률로 실패하게
        if(randNum < 3){
            kafkaTemplate.send(outboxEvent.getTopic(), outboxEvent.getPayload());
            outboxEvent.success();
            log.info("이벤트 발행에 성공하였습니다.");
        }else{
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
        return outboxEventRepository.findAllByStatusIsNot(OutboxEvent.Status.SENT);
    }
}