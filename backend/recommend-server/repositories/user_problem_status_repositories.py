from datetime import datetime, timedelta

from db import mongo_db

# 컬렉션 선택
user_problem_status_collection = mongo_db['userProblemStatus']

def get_user_problem_status(user_id):
    return list(user_problem_status_collection.distinct(
        "problem._id",
        {"userId": user_id, "status": "CORRECT"}
    ))
def get_correct_sevendays(user_id):
    # 현재 시간에서 7일(일주일) 전의 날짜를 계산
    one_week_ago = datetime.now() - timedelta(days=7)

    # userId와 일주일 이내의 lastSolvedDate 조건으로 문서 필터링
    return list(user_problem_status_collection.find({
        'userId': user_id,  # 특정 사용자 ID
        'modifiedDate': {'$gte': one_week_ago},  # modifiedDate one_week_ago 이후인 문서만
        'status': 'CORRECT'
    }))

def get_wrong_sevendays(user_id):
    # 현재 시간에서 7일(일주일) 전의 날짜를 계산
    one_week_ago = datetime.now() - timedelta(days=7)

    # userId와 일주일 이내의 lastSolvedDate 조건으로 문서 필터링
    return list(user_problem_status_collection.find({
        'userId': user_id,  # 특정 사용자 ID
        'modifiedDate': {'$gte': one_week_ago},  # modifiedDate one_week_ago 이후인 문서만
        'status': 'WRONG'
    }))

    for status in problem_status:
        status["_id"] = str(status["_id"])
        status["problem"]["_id"] = str(status["problem"]["_id"])

    return problem_status

def get_top_n_classifications(n: int):
    # 수정 필요
    top_classifications = [
        {"classification": "시사", "count": 5},
        {"classification": "의사소통", "count": 3},
    ]
    return [item["classification"] for item in top_classifications[:n]]