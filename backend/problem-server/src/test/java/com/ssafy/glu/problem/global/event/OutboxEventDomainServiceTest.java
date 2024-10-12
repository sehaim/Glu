package com.ssafy.glu.problem.global.event;

import com.ssafy.glu.problem.global.event.document.OutboxEvent;
import com.ssafy.glu.problem.global.event.repository.OutboxEventRepository;
import com.ssafy.glu.problem.global.event.service.OutboxEventDomainService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class OutboxEventDomainServiceTest {

    @InjectMocks
    private OutboxEventDomainService outboxEventDomainService;

    @Mock
    private OutboxEventRepository outboxEventRepository;

    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveOutbox_ShouldSaveEvent() {
        // Arrange
        String topic = "test-topic";
        Object event = "test-payload";
        OutboxEvent outboxEvent = new OutboxEvent(topic, event, OutboxEvent.Status.PENDING);
        when(outboxEventRepository.save(any(OutboxEvent.class))).thenReturn(outboxEvent);

        // Act
        OutboxEvent result = outboxEventDomainService.saveOutbox(topic, event);

        // Assert
        verify(outboxEventRepository, times(1)).save(any(OutboxEvent.class));
        assertThat(result.getTopic()).isEqualTo(topic);
    }

    @Test
    void sendEvent_ShouldSendKafkaMessage() {
        // Arrange
        String topic = "test-topic";
        Object event = "test-payload";
        OutboxEvent outboxEvent = new OutboxEvent(topic, event, OutboxEvent.Status.PENDING);
        when(kafkaTemplate.send(anyString(), any())).thenReturn(null);

        // Act
        outboxEventDomainService.sendEvent(outboxEvent);

        verify(kafkaTemplate, times(1)).send(topic, event);
        // Assert
        assertEquals(OutboxEvent.Status.SENT, outboxEvent.getStatus());
        verify(outboxEventRepository, times(1)).save(outboxEvent);
    }
}
