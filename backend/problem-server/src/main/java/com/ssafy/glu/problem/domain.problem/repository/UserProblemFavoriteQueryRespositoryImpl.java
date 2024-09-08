package com.ssafy.glu.problem.domain.problem.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ReplaceRootOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.ssafy.glu.problem.domain.problem.domain.Problem;

public class UserProblemFavoriteQueryRespositoryImpl implements UserProblemFavoriteQueryRespository {
	private final MongoTemplate template;

	public UserProblemFavoriteQueryRespositoryImpl(MongoTemplate template) {
		this.template = template;
	}

	@Override
	public Page<Problem> findAllFavoriteProblem(Long userId, Pageable pageable) {
		// 유저 ID로 필터링
		Criteria criteria = new Criteria();
		criteria.and("userId").is(userId);

		// 집계 파이프라인 구성
		Aggregation aggregation = Aggregation.newAggregation(
			matchByCriteria(criteria),            // userId 기준으로 필터링
			sortByCreatedDate(),                  // 생성일 기준 내림차순 정렬
			// groupByProblemId(),                   // problemId 기준으로 중복 제거
			lookupOperation(),                    // problem 컬렉션과 조인
			unwindProblem(),                      // 문제 필드 배열을 풀어줌
			matchNonNullProblem(),                // problem 필드가 null이 아닌 경우만 필터링
			replaceRootOperation(),               // problem을 루트로 설정
			skipPagination(pageable.getOffset()), // 페이지 오프셋 설정
			limitPagination(pageable.getPageSize()) // 페이지 크기 설정
		);

		// 결과 추출
		List<Problem> favoriteProblems = template.aggregate(aggregation, "userProblemFavorite", Problem.class)
			.getMappedResults();
		for (Problem p : favoriteProblems)
			System.out.println(p.getProblemId() + " " + p.getTitle() + " " + p.getContent()); // 결과 로그 확인

		long total = template.count(new Query(criteria), "userProblemFavorite");

		// 페이징된 결과 반환
		return new PageImpl<>(favoriteProblems, pageable, total);
	}

	private GroupOperation groupByProblemId() {
		return Aggregation.group("problem.problemId")
			.first("problem").as("problem");
	}

	// Criteria를 기반으로 match 단계 설정
	private MatchOperation matchByCriteria(Criteria criteria) {
		return Aggregation.match(criteria);
	}

	// createdDate 기준으로 내림차순 정렬
	private SortOperation sortByCreatedDate() {
		return Aggregation.sort(Sort.by(Sort.Direction.DESC, "createdDate"));
	}

	// problem 컬렉션과 join
	private LookupOperation lookupOperation() {
		// DBRef에서 problem.$id로 참조
		return Aggregation.lookup("problem", "problem.$id", "_id", "problem");
	}

	// 배열을 개별 문서로 풀어줌
	private UnwindOperation unwindProblem() {
		return Aggregation.unwind("problem", true); // 빈 배열도 허용
	}

	// problem 필드가 null이 아닌 경우만 처리
	private MatchOperation matchNonNullProblem() {
		return Aggregation.match(Criteria.where("problem").ne(null));
	}

	// replaceRoot로 problem 필드를 루트로 설정
	private ReplaceRootOperation replaceRootOperation() {
		return Aggregation.replaceRoot("problem");
	}

	// 페이징 처리를 위한 skip 연산
	private AggregationOperation skipPagination(long offset) {
		return Aggregation.skip(offset);
	}

	// 페이징 처리를 위한 limit 연산
	private AggregationOperation limitPagination(int size) {
		return Aggregation.limit(size);
	}
}
