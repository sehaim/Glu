from datetime import date

from pydantic import BaseModel
from typing import List

class ProblemOption(BaseModel):
    problemOptionId: int
    option: str

class ProblemLevel(BaseModel):
    problemLevelCode: str
    name: str

class ProblemType(BaseModel):
    problemTypeCode: str
    problemTypeDetailCode: str
    name: str

class Problem(BaseModel):
    problemId: int
    title: str
    content: str
    problemOptions: List[ProblemOption]
    solution: str
    problemLevel: ProblemLevel
    problemType: ProblemType

class ProblemsResponse(BaseModel):
    problems: List[Problem]

class UserResponse(BaseModel):
    user_id: int
    login_id: str
    nickname: str
    password: str
    birth: date
    is_deleted: bool
    stage: int
    exp: int
    day_count: int

    class Config:
        from_attributes = True