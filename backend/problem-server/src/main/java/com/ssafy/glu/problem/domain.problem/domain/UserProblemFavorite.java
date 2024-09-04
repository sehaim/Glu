package com.ssafy.glu.problem.domain.problem.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@ToString
public class UserProblemFavorite {
    @Id
    private String userProblemFavoriteId;

    private final Long userId;

    private final String problemId;

    @Builder
    public UserProblemFavorite(Long userId, String problemId) {
        this.userId = userId;
        this.problemId = problemId;
    }
}
