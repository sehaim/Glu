package com.ssafy.glu.user.domain.user.event;

import java.util.List;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.glu.user.domain.user.domain.UserProblemType;
import com.ssafy.glu.user.domain.user.dto.event.ProblemSolvedEvent;
import com.ssafy.glu.user.domain.user.repository.UserProblemTypeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProblemSolvedEventConsumer {

	private final UserProblemTypeRepository userProblemTypeRepository;

	@KafkaListener(topics = "${kafka.topic.problem-solved}", groupId = "user-score")
	@Transactional
	public void consumeProblemSolvedEventForScoreUpdate(ProblemSolvedEvent event) {
		log.info("[Kafka] 유저 점수 업데이트, event : {}", event);
		List<UserProblemType> userProblemTypeList  = userProblemTypeRepository.findAllByUserId(event.userId());

		for (UserProblemType userProblemType : userProblemTypeList ) {
			// Enum 비교는 == 사용
			if (userProblemType.getProblemTypeCode() == event.problem().problemTypeCode()) {
				userProblemType.updateScore(event.result().acquiredScore());
				break;
			}
		}
	}
}
