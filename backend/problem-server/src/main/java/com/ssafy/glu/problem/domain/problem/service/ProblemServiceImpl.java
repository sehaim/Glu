package com.ssafy.glu.problem.domain.problem.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ssafy.glu.problem.domain.problem.domain.Problem;
import com.ssafy.glu.problem.domain.problem.domain.ProblemMemo;
import com.ssafy.glu.problem.domain.problem.domain.UserProblemLog;
import com.ssafy.glu.problem.domain.problem.domain.UserProblemStatus;
import com.ssafy.glu.problem.domain.problem.dto.request.ProblemMemoCreateRequest;
import com.ssafy.glu.problem.domain.problem.dto.request.ProblemMemoUpdateRequest;
import com.ssafy.glu.problem.domain.problem.dto.request.ProblemSearchCondition;
import com.ssafy.glu.problem.domain.problem.dto.request.ProblemSolveRequest;
import com.ssafy.glu.problem.domain.problem.dto.response.ProblemBaseResponse;
import com.ssafy.glu.problem.domain.problem.dto.response.ProblemGradingResponse;
import com.ssafy.glu.problem.domain.problem.dto.response.ProblemMemoResponse;
import com.ssafy.glu.problem.domain.problem.exception.problem.ProblemNotFoundException;
import com.ssafy.glu.problem.domain.problem.exception.status.UserProblemStatusNotFoundException;
import com.ssafy.glu.problem.domain.problem.repository.ProblemRepository;
import com.ssafy.glu.problem.domain.problem.repository.UserProblemLogRepository;
import com.ssafy.glu.problem.domain.problem.repository.UserProblemStatusRepository;
import com.ssafy.glu.problem.global.util.PageUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProblemServiceImpl implements ProblemService {
	private final ProblemRepository problemRepository;
	private final UserProblemLogRepository userProblemLogRepository;
	private final UserProblemStatusRepository userProblemStatusRepository;

	@Override
	public Page<ProblemBaseResponse> getProblemList(Long userId, ProblemSearchCondition condition,
		Pageable pageable) {
		return userProblemStatusRepository.findAllProblemByCondition(userId, condition, pageable)
			.map(ProblemBaseResponse::of);
	}

	@Override
	public ProblemMemoResponse createProblemMemo(Long userId, String problemId, ProblemMemoCreateRequest request) {
		// userId와 problemId로 UserProblemStatus를 찾기
		UserProblemStatus userProblemStatus = userProblemStatusRepository.findByUserIdAndProblem_ProblemId(userId,
				problemId)
			.orElseThrow(UserProblemStatusNotFoundException::new);

		// 비즈니스 로직 분리
		ProblemMemo memo = userProblemStatus.addMemo(request.content());

		// 변경된 상태 저장
		userProblemStatusRepository.save(userProblemStatus);

		// 메모 저장 후 응답 생성
		return ProblemMemoResponse.of(memo);
	}

	@Override
	public ProblemMemoResponse updateProblemMemo(Long userId, String problemId, ProblemMemoUpdateRequest request) {
		UserProblemStatus userProblemStatus = userProblemStatusRepository.findByUserIdAndProblem_ProblemId(userId,
				problemId)
			.orElseThrow(UserProblemStatusNotFoundException::new);

		// 비즈니스 로직 분리
		ProblemMemo memo = userProblemStatus.updateMemo(request.memoIndex(), request.content());

		// 변경된 상태 저장
		userProblemStatusRepository.save(userProblemStatus);

		// 메모 수정 후 응답 생성
		return ProblemMemoResponse.of(memo);
	}

	@Override
	public void deleteProblemMemo(Long userId, String problemId, Long memoIndex) {
		UserProblemStatus userProblemStatus = userProblemStatusRepository.findByUserIdAndProblem_ProblemId(userId,
				problemId)
			.orElseThrow(UserProblemStatusNotFoundException::new);

		userProblemStatus.deleteMemo(memoIndex);

		userProblemStatusRepository.save(userProblemStatus);
	}

	@Override
	public Page<ProblemMemoResponse> getProblemMemoList(Long userId, String problemId, Pageable pageable) {
		UserProblemStatus userProblemStatus = userProblemStatusRepository.findByUserIdAndProblem_ProblemId(userId,
			problemId).orElseThrow(UserProblemStatusNotFoundException::new);

		List<ProblemMemo> problemMemoList = userProblemStatus.getMemoList();

		return PageUtil.convertListToPage(problemMemoList, pageable).map(ProblemMemoResponse::of);
	}

	@Override
	public void createUserProblemFavorite(Long userId, String problemId) {
		log.info("===== 문제 찜 요청 - 유저 : {}, 문제: {} =====", userId, problemId);

		UserProblemStatus userProblemStatus = userProblemStatusRepository.findByUserIdAndProblem_ProblemId(userId,
			problemId).orElseThrow(UserProblemStatusNotFoundException::new);
		log.info("===== 문제 찜 추가 - 유저 : {}, 문제: {} =====", userId, problemId);

		userProblemStatus.createFavorite();
		userProblemStatusRepository.save(userProblemStatus);
	}

	@Override
	public void deleteUserProblemFavorite(Long userId, String problemId) {
		log.info("===== 문제 찜 취소 - 유저 : {}, 문제 : {} =====", userId, problemId);

		UserProblemStatus userProblemStatus = userProblemStatusRepository.findByUserIdAndProblem_ProblemId(userId,
			problemId).orElseThrow(UserProblemStatusNotFoundException::new);

		log.info("===== 문제 찜 취소 - 유저 : {}, 문제: {} =====", userId, problemId);

		userProblemStatus.deleteFavorite();
		userProblemStatusRepository.save(userProblemStatus);
	}

	@Override
	public ProblemGradingResponse gradeProblem(Long userId, String problemId, ProblemSolveRequest request) {
		//=== 채점 로직 ===//
		// 문제 정보 가져오기
		Problem problem = getProblemOrThrow(problemId);

		// 문제 유형별 채점
		boolean isCorrect = problem.isCorrect(request.userAnswer());

		// 문제 풀이 이력 추가
		UserProblemLog userProblemLog = userProblemLogRepository.save(request.toDocument(userId, problem, isCorrect));

		// 문제 풀이 상태 변경
		updateUserProblemStatus(userProblemLog);

		// TODO:유저 점수 획득 및 유저 풀이 수 추가
		Integer acquiredScore = isCorrect ? 1 : 0;
		Integer totalScore = 10 + acquiredScore;
		Boolean isLevelUp = false;
		String levelUpUrl = null;

		return ProblemGradingResponse.builder()
			.isCorrect(isCorrect)
			.acquiredScore(acquiredScore)
			.totalScore(totalScore)
			.isLevelUp(isLevelUp)
			.levelUpUrl(levelUpUrl)
			.build();
	}

	// 문제 풀이 상태 변경
	private void updateUserProblemStatus(UserProblemLog userProblemLog) {
		Long userId = userProblemLog.getUserId();
		Problem problem = userProblemLog.getProblem();
		UserProblemStatus userProblemStatus = userProblemStatusRepository.findByUserIdAndProblem(userId, problem)
			.orElse(
				UserProblemStatus.builder().userId(userId).problem(problem).build()
			);
		userProblemStatus.updateStatus(userProblemLog.isCorrect());
		userProblemStatusRepository.save(userProblemStatus);
	}

	// 문제 ID로 문제 가져오기
	private Problem getProblemOrThrow(String problemId) {
		return problemRepository.findById(problemId).orElseThrow(ProblemNotFoundException::new);
	}
}