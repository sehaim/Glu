from sqlalchemy.orm import Session
from models import Users

def get_user(db: Session, user_id: int):
    return db.query(Users).filter(Users.user_id == user_id).first()