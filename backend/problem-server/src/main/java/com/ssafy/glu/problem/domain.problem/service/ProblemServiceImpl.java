package com.ssafy.glu.problem.domain.problem.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ssafy.glu.problem.domain.problem.domain.Problem;
import com.ssafy.glu.problem.domain.problem.domain.ProblemMemo;
import com.ssafy.glu.problem.domain.problem.domain.UserProblemFavorite;
import com.ssafy.glu.problem.domain.problem.dto.request.ProblemMemoCreateRequest;
import com.ssafy.glu.problem.domain.problem.dto.request.ProblemMemoUpdateRequest;
import com.ssafy.glu.problem.domain.problem.dto.request.ProblemSearchCondition;
import com.ssafy.glu.problem.domain.problem.dto.response.ProblemMemoResponse;
import com.ssafy.glu.problem.domain.problem.dto.response.ProblemBaseResponse;
import com.ssafy.glu.problem.domain.problem.exception.ProblemMemoNotFoundException;
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
	public Page<ProblemBaseResponse> getProblemListByLog(Long userId, ProblemSearchCondition condition,
		Pageable pageable) {
		return userProblemLogRepository.findAllProblemInLogByCondition(userId, condition, pageable)
			.map(problem -> ProblemBaseResponse.of(problem, condition.status()));
	}

	@Override
	public ProblemMemoResponse createProblemMemo(Long userId, String problemId, ProblemMemoCreateRequest request) {
		return ProblemMemoResponse.of(problemMemoRepository.save(request.toDocument(userId,getProblemOrThrow(problemId))));
	}

	@Override
	public ProblemMemoResponse updateProblemMemo(Long userId, String problemMemoId, ProblemMemoUpdateRequest request) {
		log.info("===== 문제 메모 업데이트 요청 - 메모 Id : {}, 메모 내용 : {} =====", problemMemoId, request);

		// 문제 메모 존재 여부 확인
		ProblemMemo problemMemo = getProblemMemoOrThrow(problemMemoId);

		problemMemo.updateContent(request.content());
		log.info("===== 문제 메모 업데이트 완료 - 변경된 메모 : {} =====", problemMemo);

		problemMemoRepository.save(problemMemo);

		return new ProblemMemoResponse(problemMemo.getProblemMemoId(), problemMemo.getContent());
	}

	@Override
	public void deleteProblemMemo(Long userId, String problemMemoId) {
		log.info("===== 문제 메모 삭제 요청 - 메모 Id : {} =====", problemMemoId);
		problemMemoRepository.deleteById(problemMemoId);
	}

	@Override
	public Page<ProblemMemoResponse> getProblemMemoList(Long userId, String problemId, Pageable pageable) {
		Problem problem = problemRepository.findById(problemId).orElseThrow(ProblemNotFoundException::new);

		return problemMemoRepository.findAllByProblemOrderByCreatedDateDesc(problem, pageable)
			.map(ProblemMemoResponse::of);
	}

	@Override
	public void createUserProblemFavorite(Long userId, String problemId) {
		log.info("===== 문제 찜 요청 - 유저 : {}, 문제: {} =====", userId, problemId);

		// 문제 존재 여부 확인
		Problem problem = getProblemOrThrow(problemId);
		log.info("===== 문제 [{}] 찾았습니다 =====", problem);

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

}