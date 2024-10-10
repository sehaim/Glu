import random
from typing import List

import numpy as np
from bson import ObjectId

from db import mongo_db
from models import Problem

# 컬렉션 선택
problem_collection = mongo_db['problem']
user_problem_status_collection = mongo_db['userProblemStatus']


def get_problem_by_id(problem_id: str):
    try:
        # 문자열 ID를 ObjectId로 변환하여 MongoDB에서 조회
        problem_data = problem_collection.find_one({"_id": ObjectId(problem_id)})

        # 문제가 존재하지 않을 경우 None 반환
        if problem_data is None:
            return None

        # MongoDB의 데이터를 Problem 모델로 변환
        problem = Problem(**problem_data)

        return problem

    except Exception as e:
        print(f"Error occurred while fetching problem by ID: {e}")
        return None


def get_problem_by_ids(problem_ids: list[str]):
    try:
        # 문자열 ID를 ObjectId로 변환하여 MongoDB에서 조회
        object_ids = [ObjectId(pid) for pid in problem_ids]
        problems_data = list(problem_collection.find({"_id": {"$in": object_ids}}))

        # 문제가 존재하지 않을 경우 None 반환
        if not problems_data:
            return None

        # MongoDB의 데이터를 Problem 모델로 변환
        problems = []
        for data in problems_data:
            # ObjectId를 문자열로 변환
            problem = Problem(**data)
            problems.append(problem)

        return problems

    except Exception as e:
        print(f"Error occurred while fetching problems by IDs: {e}")
        return None


def get_random_problems_by_code_and_level(levels: List[str], detail_code: str, problem_id: str = None, limit: int = 3):

    try:
        # 기본 필터 조건 설정
        filter_conditions = {
            "problemLevelCode": {"$in": levels},
            "problemTypeDetailCode": detail_code
        }

        # problem_id가 제공된 경우, 해당 ID를 제외하는 조건 추가
        if problem_id:
            filter_conditions["_id"] = {"$ne": ObjectId(problem_id)}

        # 필터 조건에 맞는 전체 문서 수 확인
        total_count = problem_collection.count_documents(filter_conditions)

        if total_count < limit:
            return []

        # 이미 선택된 인덱스 추적을 위한 세트
        selected_indices = set()
        problems: List[Problem] = []

        # 총 limit개의 문서를 선택
        for _ in range(limit):
            while True:
                # 무작위로 인덱스를 생성
                random_index = random.randint(0, total_count - 1)

                # 이미 선택한 인덱스는 건너뜀
                if random_index not in selected_indices:
                    selected_indices.add(random_index)
                    break

            # 무작위 인덱스의 문서를 조회 (skip을 사용하여 해당 위치로 이동 후 limit(1))
            random_problem = problem_collection.find(filter_conditions).skip(random_index).limit(1)

            # 조회된 결과를 리스트로 변환하고 첫 번째 문서 추가
            problem_data = list(random_problem)[0]
            # _id 필드를 그대로 사용
            problem = Problem(**problem_data)
            problems.append(problem)

        return problems

    except Exception as e:
        print(f"Error occurred while fetching problems: {e}")
        return []



def cosine_similarity(vec_a: list[float], vec_b: list[float]) -> float:
    """두 벡터 간의 코사인 유사도를 계산합니다."""
    if len(vec_a) != len(vec_b):
        raise ValueError("두 벡터는 동일한 차원을 가져야 합니다.")

    # NumPy를 사용하여 벡터를 배열로 변환
    a = np.array(vec_a)
    b = np.array(vec_b)

    # 내적 계산
    dot_product = np.dot(a, b)

    # 벡터 크기 계산
    norm_a = np.linalg.norm(a)
    norm_b = np.linalg.norm(b)

    # 코사인 유사도 계산
    if norm_a == 0 or norm_b == 0:
        return 0.0  # 0으로 나눌 수 없으므로 유사도 0으로 설정

    return dot_product / (norm_a * norm_b)


def get_similar(level_code: str, type_detail_code: str, vector: list[float], problem_id: str):
    problem_data = problem_collection.find({
        "problemLevelCode": level_code,
        "problemTypeDetailCode": type_detail_code,
        "_id": {"$ne": ObjectId(problem_id)}
    })

    problems_with_scores = []
    for problem_dict in problem_data:
        problem = Problem(**problem_dict)
        cosine_score = cosine_similarity(vector, problem.metadata.vector)
        problems_with_scores.append((problem, cosine_score))

    # 코사인 유사도 기준으로 정렬 (내림차순)
    sorted_problems = sorted(problems_with_scores, key=lambda x: x[1], reverse=True)

    # 상위 3개 문제의 Problem 객체만 반환
    return [problem for problem, _ in sorted_problems[:3]]


def get_random_problems_by_log(detail_code: str, levels: List[str], correct_ids: list[str],
                               wrong_ids: list[str], vector, num, problem_id_list: list[str] = None):

    # 기본 필터 조건 설정
    filter_conditions = {
        "problemLevelCode": {"$in": levels},
        "problemTypeDetailCode": detail_code
    }

    # 여러 개의 problem_id가 제공된 경우, 해당 ID들을 제외하는 조건 추가
    if problem_id_list:
        filter_conditions["_id"] = {"$nin": [ObjectId(problem_id) for problem_id in problem_id_list]}

    # MongoDB에서 문제를 조회
    problem_data = list(problem_collection.find(filter_conditions))

    problems_with_scores = []
    for problem_dict in problem_data:
        problem = Problem(**problem_dict)

        if problem.id in correct_ids:
            cosine_score = cosine_similarity(vector, problem.metadata.vector) - 1
        elif problem.id in wrong_ids:
            cosine_score = cosine_similarity(vector, problem.metadata.vector) - 0.5
        else:
            cosine_score = cosine_similarity(vector, problem.metadata.vector)

        problems_with_scores.append((problem, cosine_score))

    # 코사인 유사도 기준으로 정렬 (내림차순)
    sorted_problems = sorted(problems_with_scores, key=lambda x: x[1], reverse=True)

    # 중복된 ID를 방지하고 상위 num개의 Problem 객체를 선택
    selected_problems = []
    selected_ids = set(problem_id_list) if problem_id_list else set()  # 이미 제외된 ID들을 저장
    for problem, _ in sorted_problems:
        if problem.id not in selected_ids:
            selected_problems.append(problem)
            selected_ids.add(problem.id)
            if len(selected_problems) == num:
                break

    # 상위 num개의 Problem 객체만 반환
    return [problem for problem, _ in sorted_problems[:num]]