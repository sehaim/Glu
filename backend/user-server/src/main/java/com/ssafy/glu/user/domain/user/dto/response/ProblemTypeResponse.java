package com.ssafy.glu.user.domain.user.dto.response;

import com.ssafy.glu.user.domain.user.domain.ProblemTypeCode;

public class ProblemTypeResponse {
    private final String code;
    private final String name;

    public ProblemTypeResponse(ProblemTypeCode problemTypeCode) {
        this.code = problemTypeCode.getCode();
        this.name = problemTypeCode.getDescription();
    }

    // Getters
    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
