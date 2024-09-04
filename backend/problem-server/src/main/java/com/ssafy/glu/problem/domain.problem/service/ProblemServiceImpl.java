package com.ssafy.glu.problem.domain.problem.service;

import org.springframework.stereotype.Service;

import com.ssafy.glu.problem.domain.problem.repository.ProblemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProblemServiceImpl implements ProblemService {
	private final ProblemRepository problemRepository;
}