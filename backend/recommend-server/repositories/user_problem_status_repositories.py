from db import mongo_db

# 컬렉션 선택
user_problem_status_collection = mongo_db['userProblemStatus']

def get_user_problem_status(user_id):
    return list(user_problem_status_collection.distinct(
        "problem._id",
        {"userId": user_id, "status": "CORRECT"}
    ))