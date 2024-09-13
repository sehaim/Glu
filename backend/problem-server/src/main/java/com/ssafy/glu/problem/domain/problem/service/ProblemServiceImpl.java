package com.ssafy.glu.problem.domain.problem.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ssafy.glu.problem.domain.problem.domain.ProblemMemo;
import com.ssafy.glu.problem.domain.problem.domain.UserProblemStatus;
import com.ssafy.glu.problem.domain.problem.dto.request.ProblemMemoCreateRequest;
import com.ssafy.glu.problem.domain.problem.dto.request.ProblemMemoUpdateRequest;
import com.ssafy.glu.problem.domain.problem.dto.request.ProblemSearchCondition;
import com.ssafy.glu.problem.domain.problem.dto.response.ProblemBaseResponse;
import com.ssafy.glu.problem.domain.problem.dto.response.ProblemMemoResponse;
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
}