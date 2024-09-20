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

@Service
@RequiredArgsConstructor
public class ProblemSolvedEventConsumer {

	private final UserProblemLogRepository userProblemLogRepository;
	private final UserProblemStatusRepository userProblemStatusRepository;
	private final ProblemRepository problemRepository;

	@KafkaListener(topics = "${kafka.topic.problem-solved}", groupId = "${kafka.consumer.group-id}")
	public void consumeProblemSolvedEvent(ProblemSolvedEvent event) {
		Problem problem = getProblemOrThrow(event.problemId());

		saveUserProblemLog(event, problem);
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
			.findByUserIdAndProblem(event.userId(), problem)
			.orElse(UserProblemStatus.builder()
				.userId(event.userId())
				.problem(problem)
				.build());

		userProblemStatus.updateStatus(event.isCorrect());
		userProblemStatusRepository.save(userProblemStatus);
	}
}