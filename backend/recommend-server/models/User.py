from sqlalchemy import Column, String, Integer, Boolean, Date, DateTime, BigInteger
from sqlalchemy.ext.declarative import declarative_base

Base = declarative_base()

class Users(Base):
    __tablename__ = 'users'

    user_id = Column(BigInteger, primary_key=True, autoincrement=True)
    login_id = Column(String(255), nullable=True)
    nickname = Column(String(255), nullable=True)
    password = Column(String(255), nullable=True)
    birth = Column(Date, nullable=True)
    is_deleted = Column(Boolean, default=False)
    stage = Column(Integer, default=0)
    exp = Column(Integer, default=0)
    day_count = Column(Integer, default=0)
    created_date = Column(DateTime, nullable=True)
    modified_date = Column(DateTime, nullable=True)
