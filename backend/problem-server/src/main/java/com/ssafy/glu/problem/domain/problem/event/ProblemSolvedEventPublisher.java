package com.ssafy.glu.problem.domain.problem.event;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.ssafy.glu.problem.domain.problem.domain.Problem;
import com.ssafy.glu.problem.domain.problem.dto.event.ProblemSolvedEvent;
import com.ssafy.glu.problem.domain.problem.dto.grading.GradeResult;
import com.ssafy.glu.problem.domain.problem.dto.request.ProblemSolveRequest;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProblemSolvedEventPublisher {

	private final KafkaTemplate<String, ProblemSolvedEvent> kafkaTemplate;

	@Value("${kafka.topic.problem-solved}")
	private String problemSolvedTopic;

	public void publish(Long userId, Problem problem, GradeResult gradeResult, ProblemSolveRequest request) {
		ProblemSolvedEvent event = ProblemSolvedEvent.of(userId, problem, gradeResult, request);
		kafkaTemplate.send(problemSolvedTopic, event);
	}

	public void publish(Long userId, String testId, Problem problem, GradeResult gradeResult,
		ProblemSolveRequest request) {
		ProblemSolvedEvent event = ProblemSolvedEvent.of(userId, testId, problem, gradeResult, request);
		kafkaTemplate.send(problemSolvedTopic, event);
	}
}