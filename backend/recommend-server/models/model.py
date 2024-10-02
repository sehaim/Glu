from pydantic import BaseModel, Field, GetJsonSchemaHandler
from pydantic_core import core_schema
from typing import List, Optional, Any
from datetime import datetime
from bson import ObjectId

class PydanticObjectId(ObjectId):
    @classmethod
    def __get_pydantic_core_schema__(
        cls, source_type: Any, handler: GetJsonSchemaHandler
    ) -> core_schema.CoreSchema:
        return core_schema.json_or_python_schema(
            json_schema=core_schema.str_schema(),
            python_schema=core_schema.union_schema([
                core_schema.is_instance_schema(ObjectId),
                core_schema.chain_schema([
                    core_schema.str_schema(),
                    core_schema.no_info_plain_validator_function(cls.validate),
                ]),
            ]),
            serialization=core_schema.plain_serializer_function_ser_schema(str),
        )

    @classmethod
    def validate(cls, v):
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
    word_count: Optional[int] = None
    word_avg: Optional[int] = None
    word_hard: Optional[int] = None
    length: Optional[int] = None
    classification: Optional[int] = None
    questionTypeCode: str
    problemLevelCode: str
    problemTypeCode: str
    problemTypeDetailCode: str
    metadata: Metadata
    vector: Optional[List[int]] = None
    createdDate: datetime
    modifiedDate: datetime

    class Config:
        allow_population_by_field_name = True
        json_encoders = {
            ObjectId: str
        }