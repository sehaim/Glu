from typing import Optional

from fastapi import APIRouter, Depends, HTTPException, UploadFile, File, Header

from models import ProblemsResponse, Problem, ProblemOption, ProblemLevel, ProblemType
from repositories import get_all_problems, get_user_problem_logs, get_user_problem_status, get_problems_not_solve

router = APIRouter(prefix="/api/recommend", tags=["recommend"])

@router.get("/test/level", response_model=ProblemsResponse)
async def get_level_test(user_id: Optional[str] = Header(None, alias="X-User-Id")):
    if not user_id:
        raise HTTPException(status_code=400, detail="유저ID가 없습니다.")

    # 더미 데이터 생성
    return get_all_problems()


@router.get("/test/general", response_model=ProblemsResponse)
async def get_level_test(user_id: Optional[str] = Header(None, alias="X-User-Id")):
    if not user_id:
        raise HTTPException(status_code=400, detail="유저ID가 없습니다.")
    # 더미 데이터 생성
    return get_all_problems()


@router.get("/type", response_model=ProblemsResponse)
async def get_level_test(user_id: Optional[str] = Header(None, alias="X-User-Id")):
    if not user_id:
        raise HTTPException(status_code=400, detail="유저ID가 없습니다.")
    # 더미 데이터 생성
    return get_all_problems()


@router.get("/similar", response_model=ProblemsResponse)
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