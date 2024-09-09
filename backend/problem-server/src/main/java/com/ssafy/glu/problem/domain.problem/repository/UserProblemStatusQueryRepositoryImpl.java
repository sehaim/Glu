package com.ssafy.glu.problem.domain.problem.repository;

import static com.ssafy.glu.problem.domain.problem.domain.QUserProblemStatus.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.support.QuerydslRepositorySupport;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.ssafy.glu.problem.domain.problem.domain.Problem;
import com.ssafy.glu.problem.domain.problem.domain.UserProblemStatus;
import com.ssafy.glu.problem.domain.problem.dto.request.ProblemSearchCondition;

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
				detailTypeEq(condition.ProblemTypeDetailCode()),
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

	public BooleanExpression levelEq(String problemLevelCode) {
		return problemLevelCode != null ? userProblemStatus.problem.problemLevel.problemLevelCode.eq(problemLevelCode) :
			null;
	}

	public BooleanExpression typeEq(String problemTypeCode) {
		return problemTypeCode != null ? userProblemStatus.problem.problemType.problemTypeCode.eq(problemTypeCode) :
			null;
	}

	public BooleanExpression detailTypeEq(String problemDetailTypeCode) {
		return problemDetailTypeCode != null ?
			userProblemStatus.problem.problemTypeDetail.problemTypeDetailCode.eq(problemDetailTypeCode) : null;
	}

	public BooleanExpression memoListIsNotEmpty(Boolean hasMemo) {
		return hasMemo != null ? (hasMemo ? userProblemStatus.memoList.isNotEmpty() : userProblemStatus.memoList.isEmpty()) : null;
	}

	private BooleanExpression isFavoriteEq(Boolean isFavorite) {
		return isFavorite != null ? userProblemStatus.isFavorite.eq(isFavorite) : null;
	}
}
