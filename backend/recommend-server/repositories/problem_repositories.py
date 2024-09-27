from models import Problem
from db import mongo_db

# 컬렉션 선택
problem_collection = mongo_db['problem']
user_problem_status_collection = mongo_db['userProblemStatus']

def get_one_problem():
    return problem_collection.find_one({"problemTypeDetailCode": "PT0211"})

def get_all_problems():
    # 모든 문제를 리스트로 변환하여 반환
    return list(problem_collection.find())


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

    for unsolved_problem in unsolved_problems:
        print(unsolved_problem)

    return unsolved_problems
