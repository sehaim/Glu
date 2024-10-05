from datetime import datetime

from bson import ObjectId
from pydantic import BaseModel, Field
from typing import List, Optional, Union, Dict

from models.model import PydanticObjectId, Problem

problem_type_details = {
    "PT0111": "옳게 설명하는 문장 고르기",
    "PT0112": "문장의 해당 단어의 유의어/반의어 단어 고르기",
    "PT0121": "문맥상 올바른 맞춤법 고르기",
    "PT0211": "명시적 내용 확인하기",
    "PT0221": "뉴스 기사의 핵심어 파악하기",
    "PT0222": "뉴스 기사의 내용 파악하기 - 주장을 뒷받침하는 내용인 것 고르기",
    "PT0311": "문장을 통해 알맞은 그림 유추 고르기",
    "PT0312": "함축적 내용 추론하기",
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
    word_count: float | None = None
    word_avg: float | None = None
    word_hard: float | None = None
    length: float | None = None
    classification: int | None = None
    vector: List[float] | None = None

class ProblemResponse(BaseModel):
    id: PydanticObjectId = Field(alias="_id")  # MongoDB ObjectId와 같은 필드
    title: str
    content: str
    answer: int
    solution: str
    questionType: QuestionType
    problemLevel: ProblemLevel
    problemType: ProblemType
    problemTypeDetail: ProblemTypeDetail
    metadata: Metadata
    createdDate: datetime
    modifiedDate: datetime

    @classmethod
    def from_problem(cls, problem: Union[Problem, Dict]) -> "ProblemResponse":

        print("from_problem", problem)
        print("problem_metadata", problem.metadata)
        print("problem_metadata_options", problem.metadata.options)

        if isinstance(problem, dict):
            # MongoDB에서 가져온 '_id' 필드를 'id'로 변환
            problem_id = problem.pop('_id', None)
            if problem_id:
                problem['id'] = problem_id

            # 딕셔너리를 Problem 객체로 변환
            problem = Problem(**problem)

        return cls(
            _id=problem.id,
            title=problem.title,
            content=problem.content,
            answer=problem.answer,
            solution=problem.solution,
            questionType=QuestionType.create(problem.questionTypeCode),
            problemLevel=ProblemLevel.create(problem.problemLevelCode),
            problemType=ProblemType.create(problem.problemTypeCode),
            problemTypeDetail=ProblemTypeDetail.create(problem.problemTypeDetailCode),
            metadata=Metadata(
                options=problem.metadata.options,
                word_count=problem.metadata.word_count,
                word_avg=problem.metadata.word_avg,
                word_hard=problem.metadata.word_hard,
                length=problem.metadata.length,
                classification=problem.metadata.classification,
                vector=problem.metadata.vector
            ),
            createdDate=problem.createdDate,
            modifiedDate=problem.modifiedDate,
        )

    class Config:
        allow_population_by_field_name = True
        json_encoders = {
            ObjectId: str
        }