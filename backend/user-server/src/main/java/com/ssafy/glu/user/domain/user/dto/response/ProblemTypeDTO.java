package com.ssafy.glu.user.domain.user.dto.response;

import com.ssafy.glu.user.domain.user.domain.ProblemType;

public class ProblemTypeDTO {
    private final String code;
    private final String name;

    public ProblemTypeDTO(ProblemType problemType) {
        this.code = problemType.getCode();
        this.name = problemType.getDescription();
    }

    // Getters
    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
