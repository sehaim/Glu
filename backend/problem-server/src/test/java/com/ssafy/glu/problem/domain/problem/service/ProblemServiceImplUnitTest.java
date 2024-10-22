package com.ssafy.glu.problem.domain.problem.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import com.ssafy.glu.problem.domain.problem.domain.Problem;
import com.ssafy.glu.problem.domain.problem.domain.ProblemMemo;
import com.ssafy.glu.problem.domain.problem.domain.UserProblemStatus;
import com.ssafy.glu.problem.domain.problem.dto.request.ProblemMemoCreateRequest;
import com.ssafy.glu.problem.domain.problem.dto.request.ProblemMemoUpdateRequest;
import com.ssafy.glu.problem.domain.problem.dto.request.ProblemSearchCondition;
import com.ssafy.glu.problem.domain.problem.dto.response.ProblemBaseResponse;
import com.ssafy.glu.problem.domain.problem.dto.response.ProblemMemoResponse;
import com.ssafy.glu.problem.domain.problem.event.ProblemSolvedEventPublisher;
import com.ssafy.glu.problem.domain.problem.repository.ProblemRepository;
import com.ssafy.glu.problem.domain.problem.repository.UserProblemStatusRepository;
import com.ssafy.glu.problem.domain.user.service.UserService;
import com.ssafy.glu.problem.global.util.PageUtil;
import com.ssafy.glu.problem.util.MockFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class ProblemServiceImplUnitTest {
	@Mock
	private ProblemRepository problemRepository;
	@Mock
	private UserProblemStatusRepository userProblemStatusRepository;
	@Mock
	private ProblemSolvedEventPublisher problemSolvedEventPublisher;
	@Mock
	private ProblemGradingService problemGradingService;
	@Mock
	private UserService userService;

	@InjectMocks
	private ProblemServiceImpl problemService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void getProblem() {
		//given
		Problem problem = MockFactory.createProblem();
		when(problemRepository.findById(problem.getProblemId())).thenReturn(Optional.of(problem));

		//when
		ProblemBaseResponse foundProblem = problemService.getProblem(problem.getProblemId());

		//then
		assertThat(foundProblem.title()).isEqualTo(problem.getTitle());
		verify(problemRepository).findById(problem.getProblemId());
	}

	@Test
	void getProblemList() {
		//given
		Long userId = 1L;
		ProblemSearchCondition condition = mock(ProblemSearchCondition.class);
		Pageable pageable = mock(Pageable.class);
		List<UserProblemStatus> userProblemStatuseList = List.of(mock(UserProblemStatus.class), mock(UserProblemStatus.class));
		Page<UserProblemStatus> userProblemStatusPage = PageUtil.convertListToPage(userProblemStatuseList,pageable);
		when(userProblemStatusRepository.findAllProblemByCondition(userId, condition, pageable)).thenReturn(userProblemStatusPage);

		//when
		Page<ProblemBaseResponse> foundProblemList = problemService.getProblemList(userId, condition, pageable);

		//then
		assertThat(foundProblemList.getTotalElements()).isEqualTo(userProblemStatusPage.getTotalElements());
		verify(userProblemStatusRepository).findAllProblemByCondition(userId, condition, pageable);
	}

	@Test
	void createProblemMemo() {
		//given
		Long userId = 1L;
		String problemId = "problemId";
		ProblemMemoCreateRequest request = new ProblemMemoCreateRequest("memo content");
		UserProblemStatus userProblemStatus = MockFactory.createUserProblemStatus(userId, mock(Problem.class));
		when(userProblemStatusRepository.findByUserIdAndProblem_ProblemId(userId, problemId)).thenReturn(Optional.of(userProblemStatus));
		when(userProblemStatusRepository.save(userProblemStatus)).thenReturn(userProblemStatus);

		Long expectedMemoIndex = userProblemStatus.generateMemoIndex();

		//when
		ProblemMemoResponse problemMemo = problemService.createProblemMemo(userId, problemId, request);

		//then
		assertThat(problemMemo.memoIndex()).isEqualTo(expectedMemoIndex);
		assertThat(problemMemo.content()).isEqualTo(request.content());

		verify(userProblemStatusRepository).findByUserIdAndProblem_ProblemId(userId, problemId);
		verify(userProblemStatusRepository).save(userProblemStatus);
	}

	@Test
	void updateProblemMemo() {
		//given
		Long userId = 1L;
		String problemId = "problemId";
		UserProblemStatus userProblemStatus = MockFactory.createUserProblemStatus(userId, mock(Problem.class));

		String beforeContent = "origin content";
		ProblemMemo problemMemo = userProblemStatus.addMemo(beforeContent);

		String afterContent = "updated content";
		ProblemMemoUpdateRequest request = new ProblemMemoUpdateRequest(problemMemo.getMemoIndex(),afterContent);
		when(userProblemStatusRepository.findByUserIdAndProblem_ProblemId(userId, problemId)).thenReturn(Optional.of(userProblemStatus));
		when(userProblemStatusRepository.save(userProblemStatus)).thenReturn(userProblemStatus);

		//when
		ProblemMemoResponse updatedProblemMemo = problemService.updateProblemMemo(userId, problemId, request);

		//then
		assertThat(updatedProblemMemo.memoIndex()).isEqualTo(problemMemo.getMemoIndex());
		assertThat(updatedProblemMemo.content()).isEqualTo(afterContent);

		verify(userProblemStatusRepository).findByUserIdAndProblem_ProblemId(userId, problemId);
		verify(userProblemStatusRepository).save(userProblemStatus);
	}

	@Test
	void deleteProblemMemo() {
		//given
		Long userId = 1L;
		String problemId = "problemId";
		UserProblemStatus userProblemStatus = MockFactory.createUserProblemStatus(userId, mock(Problem.class));

		String beforeContent = "origin content";

		ProblemMemo problemMemo = userProblemStatus.addMemo(beforeContent);

		when(userProblemStatusRepository.findByUserIdAndProblem_ProblemId(userId, problemId)).thenReturn(Optional.of(userProblemStatus));
		when(userProblemStatusRepository.save(userProblemStatus)).thenReturn(userProblemStatus);

		//when
		problemService.deleteProblemMemo(userId, problemId, problemMemo.getMemoIndex());

		//then
		assertThat(userProblemStatus.getMemoList().size()).isEqualTo(0);

		verify(userProblemStatusRepository).findByUserIdAndProblem_ProblemId(userId, problemId);
		verify(userProblemStatusRepository).save(userProblemStatus);
	}

	@Test
	void getProblemMemoList() {
	}

	@Test
	void createUserProblemFavorite() {
	}

	@Test
	void deleteUserProblemFavorite() {
	}

	@Test
	void gradeProblem() {
	}
}