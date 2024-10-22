package com.ssafy.glu.problem.domain.problem.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.mongodb.MongoException;
import com.ssafy.glu.problem.domain.problem.dto.request.ProblemMemoCreateRequest;
import com.ssafy.glu.problem.domain.problem.dto.request.ProblemMemoUpdateRequest;
import com.ssafy.glu.problem.domain.problem.dto.request.ProblemSearchCondition;
import com.ssafy.glu.problem.domain.problem.dto.response.ProblemBaseResponse;
import com.ssafy.glu.problem.domain.problem.dto.response.ProblemMemoResponse;
import com.ssafy.glu.problem.domain.problem.exception.favorite.FavoriteCancelFailedException;
import com.ssafy.glu.problem.domain.problem.exception.memo.NullMemoIndexException;
import com.ssafy.glu.problem.domain.problem.exception.memo.ProblemMemoCreateFailedException;
import com.ssafy.glu.problem.domain.problem.exception.memo.ProblemMemoDeleteFailedException;
import com.ssafy.glu.problem.domain.problem.exception.memo.ProblemMemoUpdateFailedException;
import com.ssafy.glu.problem.domain.problem.exception.problem.NullProblemIdException;
import com.ssafy.glu.problem.domain.problem.exception.problem.ProblemNotFoundException;
import com.ssafy.glu.problem.domain.problem.exception.user.NullUserIdException;
import com.ssafy.glu.problem.domain.problem.repository.ProblemRepository;

public class ProblemValidationServiceTest {

	@Mock
	private ProblemService problemService;

	@Mock
	private ProblemRepository problemRepository;

	@InjectMocks
	private ProblemValidationService problemValidationService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetProblemListValidUserId() {
		Long userId = 1L;
		ProblemSearchCondition condition = ProblemSearchCondition.builder().build();
		Pageable pageable = mock(Pageable.class);
		Page<ProblemBaseResponse> page = new PageImpl<>(List.of());

		when(problemService.getProblemList(userId, condition, pageable)).thenReturn(page);

		Page<ProblemBaseResponse> result = problemValidationService.getProblemList(userId, condition, pageable);

		verify(problemService).getProblemList(userId, condition, pageable);
	}

	@Test
	void testGetProblemListNullUserIdThrowsException() {
		assertThrows(NullUserIdException.class,
			() -> problemValidationService.getProblemList(null, ProblemSearchCondition.builder().build(),
				mock(Pageable.class)));
	}

	@Test
	void testCreateProblemMemoValidInputs() {
		Long userId = 1L;
		String problemId = "problemId";
		ProblemMemoCreateRequest request = new ProblemMemoCreateRequest("content");
		ProblemMemoResponse response = new ProblemMemoResponse(1L, "content");

		when(problemRepository.existsById(problemId)).thenReturn(true);
		when(problemService.createProblemMemo(userId, problemId, request)).thenReturn(response);

		ProblemMemoResponse result = problemValidationService.createProblemMemo(userId, problemId, request);

		verify(problemService).createProblemMemo(userId, problemId, request);
	}

	@Test
	void testCreateProblemMemoNullUserIdThrowsException() {
		assertThrows(NullUserIdException.class, () -> problemValidationService.createProblemMemo(null, "problemId",
			new ProblemMemoCreateRequest("content")));
	}

	@Test
	void testCreateProblemMemoNullProblemIdThrowsException() {
		assertThrows(NullProblemIdException.class,
			() -> problemValidationService.createProblemMemo(1L, null, new ProblemMemoCreateRequest("content")));
	}

	@Test
	void testCreateProblemMemoProblemNotExistsThrowsException() {
		when(problemRepository.existsById("problemId")).thenReturn(false);

		assertThrows(ProblemNotFoundException.class,
			() -> problemValidationService.createProblemMemo(1L, "problemId", new ProblemMemoCreateRequest("content")));
	}

	@Test
	void testCreateProblemMemoMongoException() {
		when(problemRepository.existsById("problemId")).thenReturn(true);
		when(problemService.createProblemMemo(1L, "problemId", new ProblemMemoCreateRequest("content"))).thenThrow(
			new MongoException("error"));

		assertThrows(ProblemMemoCreateFailedException.class,
			() -> problemValidationService.createProblemMemo(1L, "problemId", new ProblemMemoCreateRequest("content")));
	}

	@Test
	void testUpdateProblemMemoValidInputs() {
		Long userId = 1L;
		String problemId = "problemId";
		ProblemMemoUpdateRequest request = new ProblemMemoUpdateRequest(1L, "content");

		when(problemRepository.existsById(problemId)).thenReturn(true);

		problemValidationService.updateProblemMemo(userId, problemId, request);

		verify(problemService).updateProblemMemo(userId, problemId, request);
	}

	@Test
	void testUpdateProblemMemoNullMemoIndexThrowsException() {
		assertThrows(NullMemoIndexException.class, () -> problemValidationService.updateProblemMemo(1L, "problemId",
			new ProblemMemoUpdateRequest(null, "content")));
	}

	@Test
	void testUpdateProblemMemoProblemNotExistsThrowsException() {
		when(problemRepository.existsById("problemId")).thenReturn(false);

		assertThrows(ProblemNotFoundException.class, () -> problemValidationService.updateProblemMemo(1L, "problemId",
			new ProblemMemoUpdateRequest(1L, "content")));
	}

	@Test
	void testUpdateProblemMemoMongoException() {
		when(problemRepository.existsById("problemId")).thenReturn(true);
		when(problemService.updateProblemMemo(1L, "problemId", new ProblemMemoUpdateRequest(1L, "content"))).thenThrow(
			new MongoException("error"));

		assertThrows(ProblemMemoUpdateFailedException.class,
			() -> problemValidationService.updateProblemMemo(1L, "problemId",
				new ProblemMemoUpdateRequest(1L, "content")));
	}

	@Test
	void testDeleteProblemMemoValidInputs() {
		Long userId = 1L;
		String problemId = "problemId";
		Long memoIndex = 1L;

		when(problemRepository.existsById(problemId)).thenReturn(true);

		problemValidationService.deleteProblemMemo(userId, problemId, memoIndex);

		verify(problemService).deleteProblemMemo(userId, problemId, memoIndex);
	}

	@Test
	void testDeleteProblemMemoMongoException() {
		when(problemRepository.existsById("problemId")).thenReturn(true);
		doThrow(new MongoException("error")).when(problemService).deleteProblemMemo(1L, "problemId", 1L);

		assertThrows(ProblemMemoDeleteFailedException.class,
			() -> problemValidationService.deleteProblemMemo(1L, "problemId", 1L));
	}

	@Test
	void testGetProblemMemoListValidInputs() {
		Long userId = 1L;
		String problemId = "problemId";
		Pageable pageable = mock(Pageable.class);
		Page<ProblemMemoResponse> page = new PageImpl<>(List.of());

		when(problemRepository.existsById(problemId)).thenReturn(true);
		when(problemService.getProblemMemoList(userId, problemId, pageable)).thenReturn(page);

		Page<ProblemMemoResponse> result = problemValidationService.getProblemMemoList(userId, problemId, pageable);

		verify(problemService).getProblemMemoList(userId, problemId, pageable);
	}

	@Test
	void testCreateUserProblemFavoriteValidInputs() {
		Long userId = 1L;
		String problemId = "problemId";

		when(problemRepository.existsById(problemId)).thenReturn(true);

		problemValidationService.createUserProblemFavorite(userId, problemId);

		verify(problemService).createUserProblemFavorite(userId, problemId);
	}

	@Test
	void testDeleteUserProblemFavoriteValidInputs() {
		Long userId = 1L;
		String problemId = "problemId";

		when(problemRepository.existsById(problemId)).thenReturn(true);

		problemValidationService.deleteUserProblemFavorite(userId, problemId);

		verify(problemService).deleteUserProblemFavorite(userId, problemId);
	}

	@Test
	void testDeleteUserProblemFavoriteMongoException() {
		when(problemRepository.existsById("problemId")).thenReturn(true);
		doThrow(new MongoException("error")).when(problemService).deleteUserProblemFavorite(1L, "problemId");

		assertThrows(FavoriteCancelFailedException.class,
			() -> problemValidationService.deleteUserProblemFavorite(1L, "problemId"));
	}
}
