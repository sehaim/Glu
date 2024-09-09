package com.ssafy.glu.problem.domain.problem.service;

import com.ssafy.glu.problem.domain.problem.dto.request.ProblemSearchCondition;
import com.ssafy.glu.problem.domain.problem.dto.response.ProblemBaseResponse;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ssafy.glu.problem.domain.problem.domain.Problem;
import com.ssafy.glu.problem.domain.problem.dto.request.ProblemMemoCreateRequest;
import com.ssafy.glu.problem.domain.problem.dto.request.ProblemMemoUpdateRequest;
import com.ssafy.glu.problem.domain.problem.dto.response.ProblemMemoResponse;
import com.ssafy.glu.problem.domain.problem.exception.FavoriteAlreadyRegisteredException;
import com.ssafy.glu.problem.domain.problem.exception.FavoriteCancelFailedException;
import com.ssafy.glu.problem.domain.problem.exception.FavoriteNotFoundException;
import com.ssafy.glu.problem.domain.problem.exception.FavoriteRegistrationFailedException;
import com.ssafy.glu.problem.domain.problem.exception.ProblemMemoCreateFailedException;
import com.ssafy.glu.problem.domain.problem.exception.ProblemMemoDeleteFailedException;
import com.ssafy.glu.problem.domain.problem.exception.ProblemMemoNotFoundException;
import com.ssafy.glu.problem.domain.problem.exception.ProblemMemoUpdateFailedException;
import com.ssafy.glu.problem.domain.problem.exception.ProblemNotFoundException;
import com.ssafy.glu.problem.domain.problem.repository.ProblemRepository;
import com.ssafy.glu.problem.domain.problem.repository.UserProblemFavoriteRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Primary
public class ProblemValidationService implements ProblemService {
	private final ProblemService problemService;
	private final ProblemRepository problemRepository;
	private final UserProblemFavoriteRepository userProblemFavoriteRepository;

	@Override
	public Page<ProblemBaseResponse> getProblemListByLog(Long userId, ProblemSearchCondition condition,
		Pageable pageable) {
		return problemService.getProblemListByLog(userId, condition, pageable);
	}

	@Override
	public ProblemMemoResponse createProblemMemo(Long userId, String problemId, ProblemMemoCreateRequest request) {
		log.info("===== 문제 메모 생성 요청 - 사용자 Id : {}, 문제 Id : {}, 메모 내용 : {} =====", userId, problemId, request);
		try {
			ProblemMemoResponse response = problemService.createProblemMemo(userId,problemId,request);
			log.info("===== 문제 메모 생성 완료 - 변경된 메모 : {} =====", response);
			return response;
		}catch (Exception exception){
			throw new ProblemMemoCreateFailedException(exception);
		}
	}

	@Override
	public Page<ProblemMemoResponse> getProblemMemoList(Long userId, String problemId, Pageable pageable) {
		// 검증
		log.info("검증 로직 서비스");
		Problem problem = getProblemOrThrow(problemId);
		return problemService.getProblemMemoList(userId, problemId, pageable);
	}

	@Override
	public Page<ProblemBaseResponse> getUserProblemFavoriteList(Long userId, ProblemSearchCondition condition, Pageable pageable) {
		return problemService.getUserProblemFavoriteList(userId, condition, pageable);
	}

	@Override
	public void createUserProblemFavorite(Long userId, String problemId) {
		// 검증
		log.info("검증 로직 서비스");
		log.info("===== 문제 찜 생성 요청 - 사용자 Id : {}, 문제 Id : {} =====", userId, problemId);

		Problem problem = getProblemOrThrow(problemId);

		validateFavoriteNotRegistered(userId, problem);

		try {
			problemService.createUserProblemFavorite(userId, problemId);
		} catch (Exception exception) {
			throw new FavoriteRegistrationFailedException(exception);
		}
	}

	@Override
	public void deleteUserProblemFavorite(Long userId, String problemId) {
		// 검증
		log.info("검증 로직 서비스");
		log.info("===== 문제 찜 취소 요청 - 사용자 Id : {}, 문제 Id : {} =====", userId, problemId);

		Problem problem = getProblemOrThrow(problemId);

		validateFavoriteRegistered(userId, problem);

		try {
			problemService.deleteUserProblemFavorite(userId, problemId);
		} catch (Exception exception) {
			throw new FavoriteCancelFailedException(exception);
		}
	}

	// ===== 찾기 로직 =====
	// 문제 존재 여부 판단
	private Problem getProblemOrThrow(String problemId) {
		return problemRepository.findById(problemId).orElseThrow(ProblemNotFoundException::new);
	}


	// ===== 검증 로직 =====

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
