package com.ssafy.glu.problem.domain.problem.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ssafy.glu.problem.domain.problem.domain.Problem;
import com.ssafy.glu.problem.domain.problem.domain.ProblemMemo;
import com.ssafy.glu.problem.domain.problem.domain.UserProblemFavorite;
import com.ssafy.glu.problem.domain.problem.dto.request.ProblemMemoUpdateRequest;
import com.ssafy.glu.problem.domain.problem.dto.request.UserProblemLogSearchCondition;
import com.ssafy.glu.problem.domain.problem.dto.response.ProblemMemoResponse;
import com.ssafy.glu.problem.domain.problem.dto.response.UserProblemLogResponse;
import com.ssafy.glu.problem.domain.problem.exception.FavoriteAlreadyRegisteredException;
import com.ssafy.glu.problem.domain.problem.exception.FavoriteNotFoundException;
import com.ssafy.glu.problem.domain.problem.exception.ProblemMemoNotFoundException;
import com.ssafy.glu.problem.domain.problem.exception.ProblemMemoUpdateUnauthorizedException;
import com.ssafy.glu.problem.domain.problem.exception.ProblemNotFoundException;
import com.ssafy.glu.problem.domain.problem.repository.ProblemMemoRepository;
import com.ssafy.glu.problem.domain.problem.repository.ProblemRepository;
import com.ssafy.glu.problem.domain.problem.repository.UserProblemFavoriteRepository;
import com.ssafy.glu.problem.domain.problem.repository.UserProblemLogRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProblemServiceImpl implements ProblemService {
	private final ProblemRepository problemRepository;
	private final ProblemMemoRepository problemMemoRepository;
	private final UserProblemFavoriteRepository userProblemFavoriteRepository;
	private final UserProblemLogRepository userProblemLogRepository;

	@Override
	public Page<UserProblemLogResponse> getProblemListByLog(Long userId, UserProblemLogSearchCondition condition,
		Pageable pageable) {
		return userProblemLogRepository.findAllProblemInLogByCondition(userId,condition,pageable).map(problem->UserProblemLogResponse.of(problem,condition.status()));
	}

	@Override
	public ProblemMemoResponse updateProblemMemo(Long userId, String problemMemoId, ProblemMemoUpdateRequest request) {
		log.info("===== 문제 메모 업데이트 요청 - 메모 Id : {}, 메모 내용 : {} =====", problemMemoId, request);

		// 문제 메모 존재 여부 확인
		ProblemMemo problemMemo = getProblemMemoOrThrow(problemMemoId);

		// 권한 여부 확인
		validateProblemMemoUpdateAuthorized(userId, problemMemo);

		problemMemo.updateContents(request.contents());
		log.info("===== 문제 메모 업데이트 완료 - 변경된 메모 : {} =====", problemMemo);

		problemMemoRepository.save(problemMemo);

		return new ProblemMemoResponse(problemMemo.getProblemMemoId(), problemMemo.getContents());
	}

	@Override
	public void createUserProblemFavorite(Long userId, String problemId) {
		log.info("===== 문제 찜 요청 - 유저 : {}, 문제: {} =====", userId, problemId);

		// 문제 존재 여부 확인
		Problem problem = getProblemOrThrow(problemId);
		log.info("===== 문제 [{}] 찾았습니다 =====", problem);

		// 찜 존재 여부 확인
		validateFavoriteNotRegistered(userId, problem);

		UserProblemFavorite userProblemFavorite = UserProblemFavorite.builder()
			.userId(userId)
			.problem(problem)
			.build();
		log.info("===== 문제 찜 추가 - 유저 : {}, 문제: {} =====", userId, problemId);

		userProblemFavoriteRepository.save(userProblemFavorite);
	}

	@Override
	public void deleteUserProblemFavorite(Long userId, String problemId) {
		log.info("===== 문제 찜 취소 - 유저 : {}, 문제 : {} =====", userId, problemId);

		// 문제 존재 여부 확인
		Problem problem = getProblemOrThrow(problemId);
		log.info("===== 문제 [{}] 찾았습니다 =====", problem);

		// 찜 존재 여부 확인
		validateFavoriteRegistered(userId, problem);

		userProblemFavoriteRepository.deleteByUserIdAndProblem(userId, problem);
	}

	// 문제 존재 여부 판단
	private Problem getProblemOrThrow(String problemId) {
		return problemRepository.findById(problemId).orElseThrow(ProblemNotFoundException::new);
	}

	// 문제 메모 존재 여부 판단
	private ProblemMemo getProblemMemoOrThrow(String problemMemoId) {
		return problemMemoRepository.findById(problemMemoId).orElseThrow(ProblemMemoNotFoundException::new);
	}

	// 사용자가 문제 메모를 수정할 수 있는지 권한 확인
	private void validateProblemMemoUpdateAuthorized(Long userId, ProblemMemo problemMemo) {
		if (userId != problemMemo.getUserId()) {
			log.warn("===== 사용자 [{}]가 문제 메모 [{}]를 수정할 권한이 없습니다 =====", userId, problemMemo);
			throw new ProblemMemoUpdateUnauthorizedException();
		}
	}

	// 이전에 찜 했는지 판단 => 이전에 찜이 있으면 오류
	private void validateFavoriteNotRegistered(Long userId, Problem problem) {
		if (userProblemFavoriteRepository.existsByUserIdAndProblem(userId, problem)) {
			log.warn("===== 사용자 [{}]가 이미 문제 [{}]를 찜한 상태 =====", userId, problem);
			throw new FavoriteAlreadyRegisteredException();
		}
	}

	// 이전에 찜 했는지 판단 => 이전에 찜이 없으면 오류
	private void validateFavoriteRegistered(Long userId, Problem problem) {
		if (!userProblemFavoriteRepository.existsByUserIdAndProblem(userId, problem)) {
			log.warn("===== 사용자 [{}]가 [{}]를 찜하지 않은 상태 =====", userId, problem);
			throw new FavoriteNotFoundException();
		}
	}
}