import os
from dotenv import load_dotenv
from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker
from sqlalchemy.ext.declarative import declarative_base

# .env 파일 로드
load_dotenv("config/glu-recommend.env")

# 환경 변수에서 설정 불러오기
maria_url = os.getenv('MARIADB_URL')
maria_database = os.getenv('MARIADB_DATABASE')

# SQLAlchemy 엔진 및 세션 설정
engine = create_engine(f"{maria_url}/{maria_database}")
SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)
Base = declarative_base()