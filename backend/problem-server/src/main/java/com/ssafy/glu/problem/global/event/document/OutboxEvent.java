package com.ssafy.glu.problem.global.event.document;

import com.ssafy.glu.problem.global.shared.BaseTimeDocument;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "outbox")
@ToString
public class OutboxEvent extends BaseTimeDocument {
    @Id
    private String id;
    private String topic; // kafka topic
    private Object payload; // event data
    private OutboxEvent.Status status; // PENDING, SENT, FAILED

    @Builder
    public OutboxEvent(String topic, Object payload, OutboxEvent.Status status) {
        this.topic = topic;
        this.payload = payload;
        this.status = status == null ? OutboxEvent.Status.PENDING : status;
    }

    public void success() {
        status = Status.SENT;
    }

    public void fail() {
        status = Status.FAILED;
    }

    public enum Status{
        PENDING, // 보류 - 전송 전
        SENT, // 전송 성공
        FAILED; // 전송 실패
    }
}