from fastapi import APIRouter, Depends, HTTPException
from models import ProblemsResponse, Problem, ProblemOption, ProblemLevel, ProblemType
from repositories import save_problem, get_user, get_all_problems, get_user_problem_logs, get_user_problem_status, get_problems_not_solve
from sqlalchemy.orm import Session
from db import get_maria_db

router = APIRouter(prefix="/api/recommend", tags=["recommend"])

# 예시 데이터
example_problem = Problem(
    problemId=1,
    title="Sample Problem 1",
    content="This is a sample problem content.",
    problemOptions=[
        ProblemOption(problemOptionId=1, option="Option A"),
        ProblemOption(problemOptionId=2, option="Option B"),
        ProblemOption(problemOptionId=3, option="Option C"),
        ProblemOption(problemOptionId=4, option="Option D")
    ],
    solution="Sample Solution",
    problemLevel=ProblemLevel(problemLevelCode="L1", name="Level 1"),
    problemType=ProblemType(
        problemTypeCode="T1",
        problemTypeDetailCode="T1-D1",
        name="Type 1"
    )
)


@router.get("/test/level", response_model=ProblemsResponse)
async def get_level_test():
    # 더미 데이터 생성
    return get_all_problems()


@router.get("/test/general", response_model=ProblemsResponse)
async def get_level_test():
    # 더미 데이터 생성
    return get_all_problems()


@router.get("/type", response_model=ProblemsResponse)
async def get_level_test():
    # 더미 데이터 생성
    return get_all_problems()


@router.get("/similar", response_model=ProblemsResponse)
async def get_level_test():
    # 더미 데이터 생성
    return get_all_problems()


@router.post("/make")
async def make_problem():
    save_problem(example_problem)


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


@router.get("/users")
async def get_user_problem(user_id: int, db: Session = Depends(get_maria_db)):
    print("user_id", user_id)
    user = get_user(db, 1)
    print("user", user)
    if user is None:
        raise HTTPException(status_code=404, detail="User not found")
    return user

@router.get("/not_solve")
async def not_solve_problems():
    not_solve_problems = get_problems_not_solve(1)
    return "not_solve_problems"