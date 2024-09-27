from pydantic import BaseModel, Field, validator
from typing import List, Optional
from datetime import datetime
from bson import ObjectId

class PydanticObjectId(ObjectId):
    @classmethod
    def __get_validators__(cls):
        yield cls.validate

    @classmethod
    def validate(cls, v, values, field):
        if not ObjectId.is_valid(v):
            raise ValueError("Invalid ObjectId")
        return ObjectId(v)


class Metadata(BaseModel):
    options: List[str]

class Problem(BaseModel):
    id: PydanticObjectId = Field(alias="_id")
    title: str
    content: str
    answer: int
    solution: str
    word_count: int
    word_avg: int
    word_hard: int
    length: int
    classification: int
    questionTypeCode: str
    problemLevelCode: str
    problemTypeCode: str
    problemTypeDetailCode: str
    metadata: Metadata
    vector: List[int]
    createdDate: datetime
    modifiedDate: datetime

    class Config:
        allow_population_by_field_name = True
        json_encoders = {
            ObjectId: str
        }
