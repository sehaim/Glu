package com.ssafy.glu.problem.domain.test.controller;

import com.ssafy.glu.problem.domain.test.dto.request.TestSolveRequest;
import com.ssafy.glu.problem.domain.test.dto.response.TestGradingDetailResponse;
import com.ssafy.glu.problem.domain.test.dto.response.TestGradingResponse;
import com.ssafy.glu.problem.domain.test.service.TestService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.ssafy.glu.problem.global.util.HeaderUtil.USER_ID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tests")
@Slf4j
public class TestController {
    private final TestService testService;

    @PostMapping("/grading")
    public ResponseEntity<TestGradingResponse> getTestGradingResult(@RequestHeader(USER_ID) Long userId,
                                                                    @RequestBody TestSolveRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(testService.gradeTest(userId, request));
    }

    @GetMapping
    public ResponseEntity<Page<TestGradingDetailResponse>> getTestList(@Parameter(hidden = true) @RequestHeader(USER_ID) Long userId, Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(testService.getTestList(userId, pageable));
    }

}
