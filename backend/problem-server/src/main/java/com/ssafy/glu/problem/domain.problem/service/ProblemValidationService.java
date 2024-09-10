package com.ssafy.glu.problem.domain.problem.service;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mongodb.MongoException;
import com.ssafy.glu.problem.domain.problem.domain.Problem;
import com.ssafy.glu.problem.domain.problem.dto.request.ProblemMemoCreateRequest;
import com.ssafy.glu.problem.domain.problem.dto.request.ProblemMemoUpdateRequest;
import com.ssafy.glu.problem.domain.problem.dto.request.ProblemSearchCondition;
import com.ssafy.glu.problem.domain.problem.dto.response.ProblemBaseResponse;
import com.ssafy.glu.problem.domain.problem.dto.response.ProblemMemoResponse;
import com.ssafy.glu.problem.domain.problem.exception.favorite.FavoriteAlreadyRegisteredException;
import com.ssafy.glu.problem.domain.problem.exception.favorite.FavoriteCancelFailedException;
import com.ssafy.glu.problem.domain.problem.exception.favorite.FavoriteNotRegisteredException;
import com.ssafy.glu.problem.domain.problem.exception.favorite.FavoriteRegistrationFailedException;
import com.ssafy.glu.problem.domain.problem.exception.memo.NullMemoIndexException;
import com.ssafy.glu.problem.domain.problem.exception.memo.ProblemMemoCreateFailedException;
import com.ssafy.glu.problem.domain.problem.exception.memo.ProblemMemoDeleteFailedException;
import com.ssafy.glu.problem.domain.problem.exception.memo.ProblemMemoUpdateFailedException;
import com.ssafy.glu.problem.domain.problem.exception.problem.NullProblemIdException;
import com.ssafy.glu.problem.domain.problem.exception.problem.ProblemNotFoundException;
import com.ssafy.glu.problem.domain.problem.exception.status.UserProblemStatusNotFoundException;
import com.ssafy.glu.problem.domain.problem.exception.user.NullUserIdException;
import com.ssafy.glu.problem.domain.problem.repository.ProblemRepository;
import com.ssafy.glu.problem.domain.problem.repository.UserProblemStatusRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Primary
public class ProblemValidationService implements ProblemService {
	private final ProblemService problemService;
	private final ProblemRepository problemRepository;
	private final UserProblemStatusRepository userProblemStatusRepository;

	@Override
	public Page<ProblemBaseResponse> getProblemList(Long userId, ProblemSearchCondition condition,
		Pageable pageable) {
		validateUserIdIsNull(userId);
		return problemService.getProblemList(userId, condition, pageable);
	}

	@Override
	public ProblemMemoResponse createProblemMemo(Long userId, String problemId, ProblemMemoCreateRequest request) {
		log.info("===== 문제 메모 생성 요청 - 사용자 Id : {}, 문제 Id : {}, 메모 내용 : {} =====", userId, problemId, request);

		// Null 값 검증
		validateUserIdIsNull(userId);
		validateProblemIdIsNull(problemId);

		// Status 존재 여부 검증
		userProblemStatusRepository.findByUserIdAndProblem_ProblemId(userId, problemId)
			.orElseThrow(UserProblemStatusNotFoundException::new);

		try {
			ProblemMemoResponse response = problemService.createProblemMemo(userId, problemId, request);
			log.info("===== 문제 메모 생성 완료 - 변경된 메모 : {} =====", response);
			return response;
		} catch (Exception exception) {
			throw new ProblemMemoCreateFailedException(exception);
		}
	}

	@Override
	public ProblemMemoResponse updateProblemMemo(Long userId, String problemId, ProblemMemoUpdateRequest request) {
		log.info("===== 문제 메모 수정 요청 - 사용자 Id : {}, 문제 Id : {}, 메모 내용 : {} =====", userId, problemId, request);

		// Null 값 검증
		validateUserIdIsNull(userId);
		validateProblemIdIsNull(problemId);
		validateMemoIndexIsNull(request.memoIndex());

		try {
			return problemService.updateProblemMemo(userId, problemId, request);
		} catch (Exception exception) {
			throw new ProblemMemoUpdateFailedException(exception);
		}
	}

	@Override
	public void deleteProblemMemo(Long userId, String problemId, Long memoIndex) {
		log.info("===== 문제 메모 수정 요청 - 사용자 Id : {}, 문제 Id : {}, 메모 인덱스 : {} =====", userId, problemId, memoIndex);

		// Null 값 검증
		validateUserIdIsNull(userId);
		validateProblemIdIsNull(problemId);
		validateMemoIndexIsNull(memoIndex);

		try {
			problemService.deleteProblemMemo(userId, problemId, memoIndex);
		} catch (Exception exception) {
			throw new ProblemMemoDeleteFailedException(exception);
		}
	}

	@Override
	public Page<ProblemMemoResponse> getProblemMemoList(Long userId, String problemId, Pageable pageable) {
		// 검증
		log.info("검증 로직 서비스");

		validateUserIdIsNull(userId);
		validateProblemIdIsNull(problemId);

		Problem problem = getProblemOrThrow(problemId);
		return problemService.getProblemMemoList(userId, problemId, pageable);
	}

	@Override
	public void createUserProblemFavorite(Long userId, String problemId) {
		// 검증
		log.info("검증 로직 서비스");
		log.info("===== 문제 찜 생성 요청 - 사용자 Id : {}, 문제 Id : {} =====", userId, problemId);

		validateUserIdIsNull(userId);
		validateProblemIdIsNull(problemId);

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

		validateUserIdIsNull(userId);
		validateProblemIdIsNull(problemId);

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
	private void validateUserIdIsNull(Long userId) {
		if (userId == null) {
			throw new NullUserIdException();
		}
	}

	private void validateProblemIdIsNull(String problemId) {
		if (problemId == null) {
			throw new NullProblemIdException();
		}
	}

	private void validateMemoIndexIsNull(Long memoIndex) {
		if (memoIndex == null) {
			throw new NullMemoIndexException();
		}
	}
}
