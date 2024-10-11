from db import mongo_db

# 컬렉션 선택
collection = mongo_db['userProblemLog']

def get_user_problem_logs(user_id):
    return list(collection.find({'userId': user_id}))