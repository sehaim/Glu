import os
from dotenv import load_dotenv
from pymongo import MongoClient

# .env 파일 로드
load_dotenv("glu-recommend.env")

# 환경 변수에서 MongoDB 설정 불러오기
mongo_url = os.getenv('MONGODB_URL')
mongo_database = os.getenv('MONGODB_DATABASE')

# MongoDB 클라이언트 설정
client = MongoClient(mongo_url)
mongo_db = client[mongo_database]  # 데이터베이스 명시
