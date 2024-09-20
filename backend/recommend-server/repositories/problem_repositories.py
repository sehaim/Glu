from models import Problem
from db import mongo_db

# 컬렉션 선택
collection = mongo_db['problem']

def save_problem(problem: Problem):
    # Pydantic 모델을 dict로 변환
    problem_dict = problem.dict()

    # 데이터 삽입
    result = collection.insert_one(problem_dict)
    return result.inserted_id


def get_all_problems():
    # 모든 문제를 리스트로 변환하여 반환
    return list(collection.find())