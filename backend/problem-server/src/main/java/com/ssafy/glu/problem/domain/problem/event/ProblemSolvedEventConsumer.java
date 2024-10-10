package com.ssafy.glu.problem.domain.problem.event;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.ssafy.glu.problem.domain.problem.domain.Problem;
import com.ssafy.glu.problem.domain.problem.domain.UserProblemLog;
import com.ssafy.glu.problem.domain.problem.domain.UserProblemStatus;
import com.ssafy.glu.problem.domain.problem.dto.event.ProblemSolvedEvent;
import com.ssafy.glu.problem.domain.problem.exception.problem.ProblemNotFoundException;
import com.ssafy.glu.problem.domain.problem.repository.ProblemRepository;
import com.ssafy.glu.problem.domain.problem.repository.UserProblemLogRepository;
import com.ssafy.glu.problem.domain.problem.repository.UserProblemStatusRepository;
import com.ssafy.glu.problem.domain.test.domain.Test;
import com.ssafy.glu.problem.domain.test.repository.TestRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProblemSolvedEventConsumer {

	private final UserProblemLogRepository userProblemLogRepository;
	private final UserProblemStatusRepository userProblemStatusRepository;
	private final ProblemRepository problemRepository;
	private final TestRepository testRepository;

	@KafkaListener(topics = "${kafka.topic.problem-solved}", groupId = "${kafka.consumer.group-id.user-problem-log}")
	public void consumeProblemSolvedEventForLog(ProblemSolvedEvent event) {
		Problem problem = getProblemOrThrow(event.problemId());

		UserProblemLog userProblemLog = saveUserProblemLog(event, problem);

		if (event.testId() != null) {
			// 테스트 찾기
			Test test = testRepository.findById(event.testId()).orElseThrow();

			// 테스트에 log 저장
			test.updateUserProblemLogIdList(userProblemLog);

			testRepository.save(test);
		}
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

	private UserProblemLog saveUserProblemLog(ProblemSolvedEvent event, Problem problem) {
		return userProblemLogRepository.save(event.toProblemLog(problem));
	}

	private void updateUserProblemStatus(ProblemSolvedEvent event, Problem problem) {
		UserProblemStatus userProblemStatus = userProblemStatusRepository
			.findByUserIdAndProblemId(event.userId(), problem.getProblemId())
			.orElse(UserProblemStatus.builder()
				.userId(event.userId())
				.problem(problem)
				.build());

		userProblemStatus.updateWhenSolve(event.isCorrect(),event.solvedTime());
		userProblemStatusRepository.save(userProblemStatus);
	}
}