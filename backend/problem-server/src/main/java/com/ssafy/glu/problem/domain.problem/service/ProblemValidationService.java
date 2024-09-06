package com.ssafy.glu.problem.domain.problem.service;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ssafy.glu.problem.domain.problem.domain.Problem;
import com.ssafy.glu.problem.domain.problem.domain.ProblemMemo;
import com.ssafy.glu.problem.domain.problem.dto.request.ProblemMemoUpdateRequest;
import com.ssafy.glu.problem.domain.problem.dto.request.UserProblemLogSearchCondition;
import com.ssafy.glu.problem.domain.problem.dto.response.ProblemMemoResponse;
import com.ssafy.glu.problem.domain.problem.dto.response.UserProblemLogResponse;
import com.ssafy.glu.problem.domain.problem.exception.FavoriteAlreadyRegisteredException;
import com.ssafy.glu.problem.domain.problem.exception.FavoriteCancelFailedException;
import com.ssafy.glu.problem.domain.problem.exception.FavoriteNotFoundException;
import com.ssafy.glu.problem.domain.problem.exception.FavoriteRegistrationFailedException;
import com.ssafy.glu.problem.domain.problem.exception.ProblemMemoDeleteFailedException;
import com.ssafy.glu.problem.domain.problem.exception.ProblemMemoDeletedUnauthorizedException;
import com.ssafy.glu.problem.domain.problem.exception.ProblemMemoNotFoundException;
import com.ssafy.glu.problem.domain.problem.exception.ProblemMemoUpdateFailedException;
import com.ssafy.glu.problem.domain.problem.exception.ProblemMemoUpdateUnauthorizedException;
import com.ssafy.glu.problem.domain.problem.exception.ProblemNotFoundException;
import com.ssafy.glu.problem.domain.problem.repository.ProblemMemoRepository;
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
	private final ProblemMemoRepository problemMemoRepository;
	private final UserProblemFavoriteRepository userProblemFavoriteRepository;

	@Override
	public Page<UserProblemLogResponse> getProblemListByLog(Long userId, UserProblemLogSearchCondition condition,
		Pageable pageable) {
		return problemService.getProblemListByLog(userId, condition, pageable);
	}

	@Override
	public ProblemMemoResponse updateProblemMemo(Long userId, String problemMemoId, ProblemMemoUpdateRequest request) {
		// 검증
		log.info("검증 로직 서비스");
		log.info("===== 문제 메모 업데이트 요청 - 사용자 Id : {}, 메모 Id : {}, 메모 내용 : {} =====", userId, problemMemoId, request);

		try {
			ProblemMemo problemMemo = getProblemMemoOrThrow(problemMemoId);

			// 문제가 존재하는지랑
			validateProblemExist(problemMemo);

			// 사용자 권한이 있는지
			validateProblemMemoUpdateAuthorized(userId, problemMemo);

			return problemService.updateProblemMemo(userId, problemMemoId, request);
		} catch (Exception exception) {
			throw new ProblemMemoUpdateFailedException(exception);
		}
	}

	@Override
	public void deleteProblemMemo(Long userId, String problemMemoId) {
		// 검증
		log.info("검증 로직 서비스");
		log.info("===== 문제 메모 삭제 요청 - 사용자 Id : {}, 메모 Id : {} =====", userId, problemMemoId);

		try {
			ProblemMemo problemMemo = getProblemMemoOrThrow(problemMemoId);

			// 문제가 존재하는지랑
			validateProblemExist(problemMemo);

			// 사용자 권한이 있는지
			validateProblemMemoDeleteAuthorized(userId, problemMemo);

			problemService.deleteProblemMemo(userId, problemMemoId);
		} catch (Exception exception) {
			throw new ProblemMemoDeleteFailedException(exception);
		}
	}

	@Override
	public void createUserProblemFavorite(Long userId, String problemId) {
		// 검증
		log.info("검증 로직 서비스");
		log.info("===== 문제 찜 생성 요청 - 사용자 Id : {}, 문제 Id : {} =====", userId, problemId);

		try {
			Problem problem = getProblemOrThrow(problemId);

			validateFavoriteNotRegistered(userId, problem);

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
		try {
			Problem problem = getProblemOrThrow(problemId);

			validateFavoriteRegistered(userId, problem);

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

	// 문제 메모 존재 여부 판단
	private ProblemMemo getProblemMemoOrThrow(String problemMemoId) {
		return problemMemoRepository.findById(problemMemoId).orElseThrow(ProblemMemoNotFoundException::new);
	}

	// ===== 검증 로직 =====
	private void validateProblemExist(ProblemMemo problemMemo) {
		if (problemMemo.getProblem() == null) {
			throw new ProblemNotFoundException();
		}
	}

	// 사용자가 문제 메모를 수정할 수 있는지 권한 확인
	private void validateProblemMemoUpdateAuthorized(Long userId, ProblemMemo problemMemo) {
		if (userId != problemMemo.getUserId()) {
			log.warn("===== 사용자 [{}]가 문제 메모 [{}]를 수정할 권한이 없습니다 =====", userId, problemMemo);
			throw new ProblemMemoUpdateUnauthorizedException();
		}
	}

	// 사용자가 문제 메모를 삭제할 수 있는지 권한 확인
	private void validateProblemMemoDeleteAuthorized(Long userId, ProblemMemo problemMemo) {
		if (userId != problemMemo.getUserId()) {
			log.warn("===== 사용자 [{}]가 문제 메모 [{}]를 삭제할 권한이 없습니다 =====", userId, problemMemo);
			throw new ProblemMemoDeletedUnauthorizedException();
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
