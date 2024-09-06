package com.ssafy.glu.user.domain.user.domain;

import com.ssafy.glu.user.global.shared.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
	private Integer level = 0;

	@Builder.Default
	private Integer score = 0;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private Users user;

	private String problemTypeCode;

}
