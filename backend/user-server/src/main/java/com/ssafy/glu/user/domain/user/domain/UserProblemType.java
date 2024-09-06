package com.ssafy.glu.user.domain.user.domain;

import com.ssafy.glu.user.global.shared.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class UserProblemType extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_problem_type_id")
	private Long id;

	private Integer level;

	private Integer score;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private Users user;

	private String problemTypeCode;

	public UserProblemType(Users user, String problemTypeCode) {
		this.level = 0;
		this.score = 0;
		this.user = user;
		this.problemTypeCode = problemTypeCode;
	}
}
