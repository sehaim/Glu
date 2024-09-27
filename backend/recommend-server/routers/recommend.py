from typing import Optional, Union, List

from fastapi import APIRouter, HTTPException,  Header

from models import Problem
from repositories import get_all_problems, get_user_problem_logs, get_user_problem_status, get_problems_not_solve
from repositories.problem_repositories import get_one_problem

router = APIRouter(prefix="/api/recommend", tags=["recommend"])


@router.get("/test/level")
async def get_level_test(user_id: Optional[str] = Header(None, alias="X-User-Id")):
    # if not user_id:
    #     raise HTTPException(status_code=400, detail="유저ID가 없습니다.")

    # 더미 데이터 생성
    problem_data = get_one_problem()

    print("problem data", problem_data)

    # 날짜 형식 변환
    from datetime import datetime
    problem_data['createdDate'] = datetime.fromisoformat(problem_data['createdDate'])
    problem_data['modifiedDate'] = datetime.fromisoformat(problem_data['modifiedDate'])

    # Pydantic 모델을 사용해 데이터 변환
    problem = Problem(**problem_data)

    print("problem info" , problem)
    return problem


@router.get("/test/general")
async def get_level_test(user_id: Optional[str] = Header(None, alias="X-User-Id")):
    if not user_id:
        raise HTTPException(status_code=400, detail="유저ID가 없습니다.")
    # 더미 데이터 생성
    return get_all_problems()


@router.get("/type")
async def get_level_test(user_id: Optional[str] = Header(None, alias="X-User-Id")):
    if not user_id:
        raise HTTPException(status_code=400, detail="유저ID가 없습니다.")
    # 더미 데이터 생성
    return get_all_problems()


@router.get("/similar")
async def get_level_test(user_id: Optional[str] = Header(None, alias="X-User-Id")):
    if not user_id:
        raise HTTPException(status_code=400, detail="유저ID가 없습니다.")
    # 더미 데이터 생성
    return get_all_problems()


@router.get("/logs")
async def get_logs():
    logs = get_user_problem_logs(1)
    print(logs)
    return "logs hello"


@router.get("/status")
async def get_status():
    status = get_user_problem_status(1)
    print(status)
    return "status hello"


@router.get("/not_solve")
async def not_solve_problems():
    not_solve_problems = get_problems_not_solve(1)
    return "not_solve_problems"