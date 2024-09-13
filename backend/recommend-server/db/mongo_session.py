from pymongo import MongoClient
import yaml

# YAML 파일 경로
CONFIG_FILE = "config/glu-recommend.yaml"

# YAML 파일 읽기
def load_config():
    with open(CONFIG_FILE, "r") as file:
        config = yaml.safe_load(file)
    return config

# 설정 불러오기
config = load_config()
mongo_config = config['mongodb']

# MongoDB 클라이언트 설정
client = MongoClient(mongo_config['url'])
mongo_db = client[mongo_config['database']]  # 데이터베이스 명시