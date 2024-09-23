package com.ssafy.glu.problem.domain.problem.event;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.ssafy.glu.problem.domain.problem.domain.Problem;
import com.ssafy.glu.problem.domain.problem.domain.UserProblemStatus;
import com.ssafy.glu.problem.domain.problem.dto.event.ProblemSolvedEvent;
import com.ssafy.glu.problem.domain.problem.exception.problem.ProblemNotFoundException;
import com.ssafy.glu.problem.domain.problem.repository.ProblemRepository;
import com.ssafy.glu.problem.domain.problem.repository.UserProblemLogRepository;
import com.ssafy.glu.problem.domain.problem.repository.UserProblemStatusRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProblemSolvedEventConsumer {

	private final UserProblemLogRepository userProblemLogRepository;
	private final UserProblemStatusRepository userProblemStatusRepository;
	private final ProblemRepository problemRepository;

	@KafkaListener(topics = "${kafka.topic.problem-solved}", groupId = "${kafka.consumer.group-id.user-problem-log}")
	public void consumeProblemSolvedEventForLog(ProblemSolvedEvent event) {
		log.info("[Kafka] 문제 풀이 기록 저장, event : {}", event);
		Problem problem = getProblemOrThrow(event.problemId());
		saveUserProblemLog(event, problem);
	}

	@KafkaListener(topics = "${kafka.topic.problem-solved}", groupId = "${kafka.consumer.group-id.user-problem-status}")
	public void consumeProblemSolvedEventForStatus(ProblemSolvedEvent event) {
		log.info("[Kafka] 문제 풀이 상태 업데이트, event : {}", event);
		Problem problem = getProblemOrThrow(event.problemId());
		updateUserProblemStatus(event, problem);
	}

	private Problem getProblemOrThrow(String problemId) {
		return problemRepository.findById(problemId).orElseThrow(ProblemNotFoundException::new);
	}

	private void saveUserProblemLog(ProblemSolvedEvent event, Problem problem) {
		userProblemLogRepository.save(event.toProblemLog(problem));
	}

	private void updateUserProblemStatus(ProblemSolvedEvent event, Problem problem) {
		UserProblemStatus userProblemStatus = userProblemStatusRepository
			.findByUserIdAndProblemId(event.userId(), problem.getProblemId())
			.orElse(UserProblemStatus.builder()
				.userId(event.userId())
				.problem(problem)
				.build());

		userProblemStatus.updateWhenSolve(event.isCorrect());
		userProblemStatusRepository.save(userProblemStatus);
	}
}