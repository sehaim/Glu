package com.ssafy.glu.user.domain.user.domain;

import java.time.LocalDate;

import com.ssafy.glu.user.global.shared.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Users extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;

	private String loginId;
	private String nickname;
	private String password;
	private LocalDate birth;
	private Boolean isDeleted;
	private Integer stage;
	private Integer exp;
	private Integer dayCount;

	@Builder
	public Users(String loginId, String nickname, String password, LocalDate birth) {
		this.loginId = loginId;
		this.nickname = nickname;
		this.password = password;
		this.birth = birth;
		this.isDeleted = false;
		this.stage = -1;
		this.exp = -1;
		this.dayCount = 0;
	}

	public void updateUser(String password, String nickname) {
		this.password = password;
		this.nickname = nickname;
	}

	public void deleteUser() {
		this.isDeleted = true;
	}
}
