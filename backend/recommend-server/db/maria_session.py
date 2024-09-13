import yaml
from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker
from sqlalchemy.ext.declarative import declarative_base

# YAML 파일 경로
CONFIG_FILE = "config/glu-recommend.yaml"

# YAML 파일 읽기
def load_config():
    with open(CONFIG_FILE, "r") as file:
        config = yaml.safe_load(file)
    return config

# 설정 불러오기
config = load_config()
db_config = config['mariadb']

# 데이터베이스 URL 동적으로 생성
SQLALCHEMY_DATABASE_URL = f"mysql+pymysql://{db_config['username']}:{db_config['password']}@{db_config['host']}:{db_config['port']}/{db_config['name']}"

# SQLAlchemy 엔진 및 세션 설정
engine = create_engine(SQLALCHEMY_DATABASE_URL)
SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)
Base = declarative_base()
