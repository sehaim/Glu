from fastapi import HTTPException, Depends, APIRouter
from sqlalchemy.orm import Session
from db import get_maria_db
from repositories import get_user

async def get_user(user_id: int, db: Session = Depends(get_maria_db)):
    print("user_id", user_id)
    user = get_user(db, user_id)
    print("user", user)
    if user is None:
        raise HTTPException(status_code=404, detail="User not found")
    return user
