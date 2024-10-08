package com.ssafy.glu.user.domain.user.domain;

import com.ssafy.glu.user.global.shared.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserProblemType extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_problem_type_id")
	private Long id;

	@Builder.Default
	private Integer level = 1;

	@Builder.Default
	private Integer score = 100;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private Users user;

	@Enumerated(EnumType.STRING)
	private ProblemTypeCode problemTypeCode;

	public void updateScore(Integer score) {

		//처음이면
		if (user.getIsFirst()) {
			int age = calculateAge(user.getBirth());
			int userLevel = calculateUserLevel(age);
			this.score = Math.max(100, 90 * userLevel);
		}

		this.score = Math.max(100, score + this.score);
		this.level = Math.min(Math.max((this.score / 100), 1), 7);
	}

	public Integer getScore() {
		if (score >= 800) return 100;
		return score % 100;
	}

	// birthDate는 "yyyy-MM-dd" 형식의 문자열로 입력됩니다.
	public static int calculateAge(LocalDate birthDate) {
		// 현재 날짜와의 차이를 계산하여 나이 산출
		LocalDate today = LocalDate.now();
		return Period.between(birthDate, today).getYears();
	}

	public static int calculateUserLevel(int age) {
		if (age >= 6 && age <= 12) {
			return age - 5; // 만 6세 -> 1레벨, 만 12세 -> 7레벨
		} else if (age < 6) {
			return 1;
		} else {
			return 7;
		}
	}
}
