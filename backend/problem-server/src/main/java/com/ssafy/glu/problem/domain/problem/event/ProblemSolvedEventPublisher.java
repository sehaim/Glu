package com.ssafy.glu.problem.domain.problem.event;

import com.ssafy.glu.problem.domain.problem.domain.Problem;
import com.ssafy.glu.problem.domain.problem.dto.event.ProblemSolvedEvent;
import com.ssafy.glu.problem.domain.problem.dto.grading.GradeResult;
import com.ssafy.glu.problem.domain.problem.dto.request.ProblemSolveRequest;
import com.ssafy.glu.problem.global.event.service.OutboxEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProblemSolvedEventPublisher {
    private final OutboxEventService outboxEventService;

    @Value("${kafka.topic.problem-solved}")
    private String problemSolvedTopic;

    public void publish(Long userId, Problem problem, GradeResult gradeResult, ProblemSolveRequest request) {
        publish(userId, null, problem, gradeResult, request);
    }

    public void publish(Long userId, String testId, Problem problem, GradeResult gradeResult,
                        ProblemSolveRequest request) {
        ProblemSolvedEvent event = ProblemSolvedEvent.of(userId, testId, problem, gradeResult, request);
        outboxEventService.saveOutBoxAndSendEvent(problemSolvedTopic, event);
    }
}