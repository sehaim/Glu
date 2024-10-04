from datetime import datetime

from bson import ObjectId
from pydantic import BaseModel, Field
from typing import List, Optional

from models.model import PydanticObjectId, Problem

problem_type_details = {
    "PT0111": "옳게 설명하는 문장 고르기",
    "PT0112": "문장의 해당 단어와 비슷한 / 반대되는 단어 고르기",
    "PT0121": "어색한 문장 찾기 - 맞춤법이 올바른 문장 찾기",
    "PT0122": "동사 형용사 구분하기",
    "PT0123": "빈칸에 들어갈 알맞은 접속사 찾기",
    "PT0124": "문장 구조에 맞는 어미 구분하기",
    "PT0211": "동화 내용 확인하기 - 주어진 글(동화)을 읽고 올바른 것 옳지 않은 것 고르기",
    "PT0221": "뉴스 기사의 핵심어 파악하기",
    "PT0222": "뉴스 기사의 내용 파악하기 - 주장을 뒷받침하는 내용인 것 고르기",
    "PT0311": "그림을 보고 상황에 맞는 문장 고르기",
    "PT0312": "동화를 읽고 내용 추론하기",
    "PT0321": "뉴스 기사를 읽고 빈칸 추론하기"
}

problem_types = {
    "PT01":	"어휘 및 문법",
    "PT02":	"독해",
    "PT03":	"추론",
}

problem_levels = {
    "PL01":	"유아",
    "PL02":	"초등 1학년",
    "PL03":	"초등 2학년",
    "PL04":	"초등 3학년",
    "PL05":	"초등 4학년",
    "PL06":	"초등 5학년",
    "PL07":	"초등 6학년"
}

problem_questions = {
    "QT01":	"객관식",
    "QT02":	"단답식",
    "QT03":	"서술형"
}

class QuestionType(BaseModel):
    code: str
    name: str

    @classmethod
    def create(cls, code: str) -> "QuestionType":
        return cls(
            code=code,
            name=problem_questions[code]
        )



class ProblemLevel(BaseModel):
    code: str
    name: str

    @classmethod
    def create(cls, code: str) -> "ProblemLevel":
        return cls(
            code=code,
            name=problem_levels[code]
        )

class ProblemType(BaseModel):
    code: str
    name: str

    @classmethod
    def create(cls, code: str) -> "ProblemType":
        return cls(
            code=code,
            name=problem_types[code]
        )

class ProblemTypeDetail(BaseModel):
    code: str
    name: str

    @classmethod
    def create(cls, code: str) -> "ProblemTypeDetail":
        return cls(
            code=code,
            name=problem_type_details[code]
        )

class Metadata(BaseModel):
    options: List[str]


class ProblemResponse(BaseModel):
    id: PydanticObjectId = Field(alias="_id")  # MongoDB ObjectId와 같은 필드
    title: str
    content: str
    answer: int
    solution: str
    word_count: Optional[float] = None
    word_avg: Optional[float] = None
    word_hard: Optional[float] = None
    length: Optional[float] = None
    classification: Optional[int] = None
    questionType: QuestionType
    problemLevel: ProblemLevel
    problemType: ProblemType
    problemTypeDetail: ProblemTypeDetail
    metadata: Metadata
    vector: Optional[List[float]] = None
    createdDate: datetime
    modifiedDate: datetime

    @classmethod
    def from_problem(cls, problem: Problem) -> "ProblemResponse":
        # ObjectId를 문자열로 변환하여 ProblemResponse 객체 생성
        return cls(
            id=problem.id,
            title=problem.title,
            content=problem.content,
            answer=problem.answer,
            solution=problem.solution,
            questionType=QuestionType.create(problem.questionTypeCode),
            problemLevel=ProblemLevel.create(problem.problemLevelCode),
            problemType=ProblemType.create(problem.problemTypeCode),
            problemTypeDetail=ProblemTypeDetail.create(problem.problemTypeDetailCode),
            metadata=problem.metadata,
            createdDate=problem.createdDate,
            modifiedDate=problem.modifiedDate,
        )

    class Config:
        allow_population_by_field_name = True
        json_encoders = {
            ObjectId: str
        }
