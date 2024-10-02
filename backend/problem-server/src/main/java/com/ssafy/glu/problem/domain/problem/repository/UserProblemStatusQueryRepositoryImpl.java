package com.ssafy.glu.problem.domain.problem.repository;

import static com.ssafy.glu.problem.domain.problem.domain.QUserProblemStatus.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.support.QuerydslRepositorySupport;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.ssafy.glu.problem.domain.problem.domain.Problem;
import com.ssafy.glu.problem.domain.problem.domain.ProblemLevelCode;
import com.ssafy.glu.problem.domain.problem.domain.ProblemTypeCode;
import com.ssafy.glu.problem.domain.problem.domain.ProblemTypeDetailCode;
import com.ssafy.glu.problem.domain.problem.domain.UserProblemStatus;
import com.ssafy.glu.problem.domain.problem.dto.request.ProblemSearchCondition;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserProblemStatusQueryRepositoryImpl extends QuerydslRepositorySupport
	implements UserProblemStatusQueryRepository {

	public UserProblemStatusQueryRepositoryImpl(MongoOperations operations) {
		super(operations);
	}

	@Override
	public Page<UserProblemStatus> findAllProblemByCondition(Long userId, ProblemSearchCondition condition,
		Pageable pageable) {
		return from(userProblemStatus)
			.where(
				userIdEq(userId),
				statusEq(condition.status()),
				levelEq(condition.problemLevelCode()),
				typeEq(condition.problemTypeCode()),
				detailTypeEq(condition.problemTypeDetailCode()),
				memoListIsNotEmpty(condition.hasMemo()),
				isFavoriteEq(condition.isFavorite())
			)
			.fetchPage(pageable);
	}

	public BooleanExpression userIdEq(Long userId) {
		return userId != null ? userProblemStatus.userId.eq(userId) : null;
	}

	public BooleanExpression statusEq(Problem.Status status) {
		return status != null ? userProblemStatus.status.eq(status) : null;
	}

	public BooleanExpression levelEq(ProblemLevelCode problemLevelCode) {
		log.info("problemLevel : {}", problemLevelCode);
		return problemLevelCode != null ? userProblemStatus.problem.problemLevelCode.eq(problemLevelCode) :
			null;
	}

	public BooleanExpression typeEq(ProblemTypeCode problemTypeCode) {
		log.info("problemType : {}", problemTypeCode);
		return problemTypeCode != null ? userProblemStatus.problem.problemTypeCode.eq(problemTypeCode) :
			null;
	}

	public BooleanExpression detailTypeEq(ProblemTypeDetailCode problemTypeDetailCode) {
		log.info("problemTypeDetail : {}", problemTypeDetailCode);
		return problemTypeDetailCode != null ?
			userProblemStatus.problem.problemTypeDetailCode.eq(problemTypeDetailCode) : null;
	}

	public BooleanExpression memoListIsNotEmpty(Boolean hasMemo) {
		return hasMemo != null ?
			(hasMemo ? userProblemStatus.memoList.isNotEmpty() : userProblemStatus.memoList.isEmpty()) : null;
	}

	private BooleanExpression isFavoriteEq(Boolean isFavorite) {
		return isFavorite != null ? userProblemStatus.isFavorite.eq(isFavorite) : null;
	}
}
