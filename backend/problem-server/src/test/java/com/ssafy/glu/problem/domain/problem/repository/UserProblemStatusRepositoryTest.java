package com.ssafy.glu.problem.domain.problem.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import com.ssafy.glu.problem.domain.problem.domain.Problem;
import com.ssafy.glu.problem.domain.problem.domain.ProblemMemo;
import com.ssafy.glu.problem.domain.problem.domain.UserProblemStatus;
import com.ssafy.glu.problem.domain.problem.exception.UserProblemStatusNotFoundException;
import com.ssafy.glu.problem.util.MockFactory;

import lombok.extern.slf4j.Slf4j;

@DataMongoTest
@ActiveProfiles("test")
@DirtiesContext
@Slf4j
public class UserProblemStatusRepositoryTest {
	@Autowired
	private UserProblemStatusRepository userProblemStatusRepository;

	@Autowired
	private ProblemRepository problemRepository;

	private final int NUM_PROBLEMS = 3;
	private List<Problem> problemList;
	private final Long[] userIdList = {1L, 2L, 3L, 4L};
	private final int NUM_LOGS_PER_USER = 3;

	@BeforeEach
	public void setUp() throws InterruptedException {
		problemRepository.deleteAll();
		userProblemStatusRepository.deleteAll();

		problemList = new ArrayList<>();
		for (int i = 0; i < NUM_PROBLEMS; i++) {
			problemList.add(problemRepository.save(MockFactory.createProblem()));
		}

		// 유저별 풀이 상태 등록
		for (Long userId : userIdList) {
			for (Problem problem : problemList) {
				userProblemStatusRepository.save(MockFactory.createUserProblemStatus(userId, problem));
			}
		}
	}

	@Test
	void createMemo() {
		Long userId = userIdList[0];
		Problem problem = problemList.get(0);

		// UserProblemStatus를 userId와 problemId로 조회
		UserProblemStatus userProblemStatus = userProblemStatusRepository.findByUserIdAndProblem_ProblemId(userId,
			problem.getProblemId()).orElseThrow(UserProblemStatusNotFoundException::new);

		// Index 찾기
		Long memoIndex = userProblemStatus.getMemoList().isEmpty() ? 1L :
			userProblemStatus.getMemoList().stream().map(ProblemMemo::getMemoIndex).max(Long::compareTo).orElse(0L)
				+ 1L;

		// 메모 추가
		ProblemMemo problemMemo = ProblemMemo.builder().memoIndex(memoIndex).content("새로운 메모 내용").build();

		// 저장
		userProblemStatus.getMemoList().add(problemMemo);

		assertThat(userProblemStatus.getMemoList().size()).isEqualTo(1);
		assertThat(userProblemStatus.getMemoList().get(0).getMemoIndex()).isEqualTo(memoIndex);
		assertThat(userProblemStatus.getMemoList().get(0).getContent()).isEqualTo("새로운 메모 내용");
	}

	@Test
	void updateMemo() {
		Long userId = userIdList[0];
		Problem problem = problemList.get(0);

		// UserProblemStatus를 userId와 problemId로 조회
		UserProblemStatus userProblemStatus = userProblemStatusRepository.findByUserIdAndProblem_ProblemId(userId,
			problem.getProblemId()).orElseThrow(UserProblemStatusNotFoundException::new);

		// Index 찾기
		Long memoIndex = userProblemStatus.getMemoList().isEmpty() ? 1L :
			userProblemStatus.getMemoList().stream().map(ProblemMemo::getMemoIndex).max(Long::compareTo).orElse(0L)
				+ 1L;

		// 메모 추가
		ProblemMemo problemMemo = ProblemMemo.builder().memoIndex(memoIndex).content("새로운 메모 내용").build();

		// 저장
		userProblemStatus.getMemoList().add(problemMemo);

		userProblemStatus.updateMemo(memoIndex, "수정된 메모 내용");

		assertThat(userProblemStatus.getMemoList().size()).isEqualTo(1);
		assertThat(userProblemStatus.getMemoList().get(0).getMemoIndex()).isEqualTo(memoIndex);
		assertThat(userProblemStatus.getMemoList().get(0).getContent()).isEqualTo("수정된 메모 내용");
	}
}
