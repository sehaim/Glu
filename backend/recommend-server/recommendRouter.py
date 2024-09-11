from fastapi import APIRouter
from models import ProblemsResponse

router = APIRouter(prefix="/api/recommend")

@router.get("/test/level", response_model=ProblemsResponse)
async def get_level_test():
    # 더미 데이터 생성
    dummy_data = {
        "problems": [
            {
                "problemId": 1,
                "title": "Sample Problem 1",
                "content": "This is a sample problem content.",
                "problemOptions": [
                    {"problemOptionId": 1, "option": "Option A"},
                    {"problemOptionId": 2, "option": "Option B"},
                    {"problemOptionId": 3, "option": "Option C"},
                    {"problemOptionId": 4, "option": "Option D"}
                ],
                "solution": "Sample Solution",
                "problemLevel": {"problemLevelCode": "L1", "name": "Level 1"},
                "problemType": {
                    "problemTypeCode": "T1",
                    "problemTypeDetailCode": "T1-D1",
                    "name": "Type 1"
                }
            },
            {
                "problemId": 2,
                "title": "Sample Problem 2",
                "content": "This is another sample problem content.",
                "problemOptions": [
                    {"problemOptionId": 5, "option": "Option A"},
                    {"problemOptionId": 6, "option": "Option B"},
                    {"problemOptionId": 7, "option": "Option C"},
                    {"problemOptionId": 8, "option": "Option D"}
                ],
                "solution": "Another Sample Solution",
                "problemLevel": {"problemLevelCode": "L2", "name": "Level 2"},
                "problemType": {
                    "problemTypeCode": "T2",
                    "problemTypeDetailCode": "T2-D1",
                    "name": "Type 2"
                }
            }
        ]
    }

    return dummy_data
