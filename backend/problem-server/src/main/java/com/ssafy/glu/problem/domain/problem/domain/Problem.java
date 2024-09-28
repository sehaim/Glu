package com.ssafy.glu.problem.domain.problem.domain;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.ssafy.glu.problem.global.shared.BaseTimeDocument;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Document
@Getter
@ToString
public class Problem extends BaseTimeDocument {
	public enum Status {
		CORRECT,
		WRONG
	}

	@Id
	private String problemId;

	private final String title;

	private final String content;

	private final String answer;

	private final String solution;

	private final QuestionTypeCode questionTypeCode;

	private final ProblemLevelCode problemLevelCode;

	private final ProblemTypeDetailCode problemTypeDetailCode;

	private final ProblemTypeCode problemTypeCode;

	private final Map<String, Object> metadata;

	@Builder
	public Problem(String title, String content, String answer, String solution, ProblemLevelCode problemLevelCode,
		ProblemTypeCode problemTypeCode, ProblemTypeDetailCode problemTypeDetailCode, QuestionTypeCode questionTypeCode,
		Map<String, Object> metadata) {
		this.title = title;
		this.content = content;
		this.answer = answer;
		this.solution = solution;
		this.problemLevelCode = problemLevelCode;
		this.problemTypeCode = problemTypeCode;
		this.problemTypeDetailCode = problemTypeDetailCode;
		this.questionTypeCode = questionTypeCode;
		this.metadata = metadata;
	}

	//=== 비즈니스 로직 ====//

	// 맞았는지 틀렸는지 반환
	public boolean grade(String userAnswer) {
		// TODO: 구체적인 정답 여부 판별 필요
		return questionTypeCode.getGradingStrategy().isCorrect(userAnswer, answer);
	}

	public int score() {
		return problemLevelCode.score();
	}

	public int level() {
		return problemLevelCode.getLevel();
	}

}
