package com.ssafy.glu.problem.domain.problem.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import com.ssafy.glu.problem.domain.problem.domain.Problem;
import com.ssafy.glu.problem.domain.problem.domain.UserProblemLog;
import com.ssafy.glu.problem.domain.problem.dto.request.ProblemSearchCondition;
import com.ssafy.glu.problem.domain.problem.dto.request.UserProblemLogSearchCondition;
import com.ssafy.glu.problem.util.MockFactory;

import lombok.extern.slf4j.Slf4j;

@DataMongoTest
@ActiveProfiles("test")
@Slf4j
class UserProblemLogRepositoryTest {
	@Autowired
	private UserProblemLogRepository userProblemLogRepository;
	@Autowired
	private ProblemRepository problemRepository;

	private final int NUM_PROBLEMS = 3;
	private List<Problem> problemList;
	private final Long[] userIdList = {1L,2L,3L,4L};
	private final int NUM_LOGS_LOGS_PER_USER = 3;

	@BeforeEach
	public void setUp() {
		userProblemLogRepository.deleteAll();
		problemList = new ArrayList<>();
		for (int i = 0; i < NUM_PROBLEMS; i++) {
			problemList.add(problemRepository.save(MockFactory.createProblem()));
		}
		// 유저별 풀이 기록 등록
		for (Long userId : userIdList) {
			for (Problem problem : problemList) {
				userProblemLogRepository.save(MockFactory.createUserProblemLog(userId,problem,true));
				userProblemLogRepository.save(MockFactory.createUserProblemLog(userId,problem,false));
				userProblemLogRepository.save(MockFactory.createUserProblemLog(userId,problem,false));
			}
		}
	}

	@Test
	void saveUserProblemLogTest() {
		List<UserProblemLog> userProblemLogList = userProblemLogRepository.findAll();

		log.info("Saved Log : {}",userProblemLogList.get(0));

		// 문제 수 체크
		assertThat(userProblemLogList.size()).isEqualTo(userIdList.length * NUM_PROBLEMS * NUM_LOGS_LOGS_PER_USER);
	}

	@Test
	void searchUserProblemLogOfCorrectTest() {
		Long userId = userIdList[0];

		UserProblemLogSearchCondition condition = UserProblemLogSearchCondition.builder()
			.status(Problem.Status.CORRECT)
			.build();

		Page<Problem> problemList = userProblemLogRepository.findByCondition(userId, condition, Pageable.ofSize(10));

		assertThat(problemList.getTotalElements()).isEqualTo(3);
	}
	@Test
	void searchUserProblemLogOfWrongTest() {
		Long userId = userIdList[0];

		UserProblemLogSearchCondition condition = UserProblemLogSearchCondition.builder()
			.status(Problem.Status.WRONG)
			.build();

		Page<Problem> result = userProblemLogRepository.findByCondition(userId, condition, Pageable.ofSize(10));

		userProblemLogRepository.save(MockFactory.createUserProblemLog(userId,problemList.get(0),false));

		log.info("검색된 문제 수 : {}",result.getContent().size());
		assertThat(result.getTotalElements()).isEqualTo(1);
	}
}