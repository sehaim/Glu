from models import Problem
from db import mongo_db
from typing import List, Dict

# 컬렉션 선택
problem_collection = mongo_db['problem']
user_problem_status_collection = mongo_db['userProblemStatus']

def get_one_problem():
    return problem_collection.find_one({"problemTypeDetailCode": "PT0211"})

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
