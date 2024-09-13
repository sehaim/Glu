from fastapi import HTTPException, Depends, APIRouter
from sqlalchemy.orm import Session
from db import get_db
from repositories import get_user
from models import UserResponse

router = APIRouter(prefix="/api/users", tags=["user"])

# 임시 데이터
temp = {
    "id": 1,
    "login_id": "user123",
    "nickname": "user_nickname",
    "password": "securepassword",
    "birth": "1990-01-01",
    "is_deleted": False,
    "stage": 5,
    "exp": 1200,
    "day_count": 30
}

@router.get("/{user_id}", response_model=UserResponse)
async def read_user(user_id: int, db: Session = Depends(get_db)):
    print("user_id", user_id)
    user = get_user(db, user_id)
    print("user", user)
    if user is None:
        raise HTTPException(status_code=404, detail="User not found")
    return user

@router.get("/temp/{user_id}", response_model=UserResponse)
async def read_temp_user():
    # 임시 데이터 반환
    return temp

