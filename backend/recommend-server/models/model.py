from pydantic import BaseModel
from typing import List
from enum import Enum

class ProblemOption(Enum):
    PT01 = "어휘 및 문법"
    PT02 = "독해"
    PT03 = "추론"

class ProblemLevel(Enum):
    PL01 = "유아"
    PL02 = "초등 1학년"
    PL03 = "초등 2학년"
    PL04 = "초등 3학년"
    PL05 = "초등 4학년"
    PL06 = "초등 5학년"
    PL07 = "초등 6학년"

class ProblemType(Enum):
    PT0111 = "옳게 설명하는 문장 고르기"
    PT0112 = "문장의 해당 단어와 비슷한 / 반대되는 단어 고르기"
    PT0121 = "어색한 문장 찾기 - 맞춤법이 올바른 문장 찾기"
    PT0122 = "동사 형용사 구분하기"
    PT0123 = "빈칸에 들어갈 알맞은 접속사 찾기"
    PT0124 = "문장 구조에 맞는 어미 구분하기"
    PT0211 = "동화 내용 확인하기 - 주어진 글(동화)을 읽고 올바른 것 옳지 않은 것 고르기"
    PT0221 = "뉴스 기사의 핵심어 파악하기"
    PT0222 = "뉴스 기사의 내용 파악하기 - 주장을 뒷받침하는 내용인 것 고르기"
    PT0311 = "그림을 보고 상황에 맞는 문장 고르기"
    PT0312 = "동화를 읽고 내용 추론하기"
    PT0321 = "뉴스 기사를 읽고 빈칸 추론하기"



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