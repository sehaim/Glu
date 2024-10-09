package com.ssafy.glu.auth.domain.auth.domain;

import java.time.LocalDate;

import org.springframework.util.StringUtils;

import com.ssafy.glu.auth.global.shared.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Users extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;

	private String loginId;
	private String nickname;
	private String password;
	private LocalDate birth;
	@Builder.Default
	private Boolean isDeleted = false;
	@Builder.Default
	private Integer stage = 0;
	@Builder.Default
	private Integer exp = 0;
	@Builder.Default
	private Integer dayCount = 0;
	@Builder.Default
	private Integer isFirst = 0;

}
