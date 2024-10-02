import random

import numpy as np
from bson import ObjectId

from db import mongo_db

# 컬렉션 선택
problem_collection = mongo_db['problem']
user_problem_status_collection = mongo_db['userProblemStatus']

def get_one_problem():
    return problem_collection.find_one({"problemTypeDetailCode": "PT0211"})


def get_problem_by_id(problem_id: str):
    try:
        # 문자열 ID를 ObjectId로 변환하여 MongoDB에서 조회
        problem = problem_collection.find_one({"_id": ObjectId(problem_id)})

        # 문제가 존재하지 않을 경우 None 반환
        if problem is None:
            return None

        # MongoDB의 ObjectId를 문자열로 변환
        problem["_id"] = str(problem["_id"])

        return problem

    except Exception as e:
        print(f"Error occurred while fetching problem by ID: {e}")
        return None

def get_problems_by_level_and_type(level_code:str, type_detail_code:str, problem_id: str):
    try:
        # 필터 조건에 맞는 전체 문서 수 확인
        total_count = problem_collection.count_documents({
            "problemLevelCode": level_code,
            "problemTypeDetailCode": type_detail_code,
            "_id": {"$ne": ObjectId(problem_id)}  # 주어진 problem_id와 일치하지 않는 조건
        })

        if total_count == 0:
            return []

        print("total_count", total_count)
        print("total_count", type(total_count))

        # 이미 선택된 인덱스 추적을 위한 세트
        selected_indices = set()
        problems = []

        # 총 3개의 문서를 선택
        for _ in range(3):
            while True:
                # 무작위로 인덱스를 생성
                random_index = random.randint(0, total_count - 2)

                # 이미 선택한 인덱스는 건너뜀
                if random_index not in selected_indices:
                    selected_indices.add(random_index)
                    break

            # 무작위 인덱스의 문서를 조회 (skip을 사용하여 해당 위치로 이동 후 limit(1))
            random_problem = problem_collection.find({
                "problemLevelCode": level_code,
                "problemTypeDetailCode": type_detail_code
            }).skip(random_index).limit(1)

            # 조회된 결과를 리스트로 변환하고 첫 번째 문서 추가
            problem = list(random_problem)[0]
            problem["_id"] = str(problem["_id"])  # ObjectId를 문자열로 변환
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

def get_similar(level_code:str, type_detail_code:str, vector: list[float], problem_id: str):
    problem_data = problem_collection.find({"problemLevelCode": level_code,
                                            "problemTypeDetailCode": type_detail_code,
                                            "_id": {"$ne": ObjectId(problem_id)} })

    problems_with_scores = []
    for problem in problem_data:
        problem["_id"] = str(problem["_id"])
        problem["cosine_score"] = cosine_similarity(vector, problem["vector"])
        problems_with_scores.append(problem)

    # 코사인 유사도 기준으로 정렬 (내림차순)
    sorted_problems = sorted(problems_with_scores, key=lambda x: x["cosine_score"], reverse=True)

    # 상위 3개 문제 반환
    return sorted_problems[:3]

def get_all_problems():
    # 모든 문제를 리스트로 변환하여 반환
    return list(problem_collection.find())

def get_random_problems_by_code_and_level(detail_code: str, level: int, limit: int):
    # MongoDB에서 문제를 조회
    problems = list(problem_collection.find({
        "problemTypeDetailCode": detail_code,
        "problemLevelCode": f"PL0{level}"
    }).limit(limit))

    # MongoDB 결과에서 ObjectId를 문자열로 변환
    for problem in problems:
        problem["_id"] = str(problem["_id"])  # ObjectId를 문자열로 변환

    return problems

def get_problems_by_detail_code(detail_code: str, limit: int = 2):
    return list(problem_collection.find({"problemTypeDetailCode": detail_code}).limit(limit))


def get_problems_not_solve(user_id: int):
    # 사용자가 이미 푼 문제의 ID를 조회
    solved_problem_ids = user_problem_status_collection.distinct(
        "problem._id",
        {"userId": user_id, "status": "CORRECT"}
    )

    # 아직 풀지 않은 문제를 조회
    unsolved_problems = list(problem_collection.find(
        {"_id": {"$nin": solved_problem_ids}}
    ))

    return unsolved_problems
