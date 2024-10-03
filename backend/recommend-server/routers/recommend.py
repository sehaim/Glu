from collections import defaultdict
from typing import Optional

import random
import numpy as np
from fastapi import APIRouter, HTTPException, Header

from repositories import get_problems_not_solve, get_wrong_sevendays, get_correct_sevendays
from repositories.problem_repositories import (
    get_problem_by_id,
    get_problem_by_ids,
    get_problems_by_level_and_type,
    get_similar, get_random_problems_by_log)
from repositories.problem_repositories import (
    get_random_problems_by_code_and_level,
    get_random_problems_by_code_and_level_and_classification
)
from repositories.user_problem_status_repositories import get_top_n_classifications

router = APIRouter(prefix="/api/recommend", tags=["recommend"])

classifications = [
    "의사소통", "예술경험", "신체운동_건강", "자연탐구",
    "사회관계", "시사", "과학", "문화",
    "사설", "교육", "경제", "nie"
]
story_classifications = [0, 1, 2, 3, 4]
news_classifications = [5, 6, 7, 8, 9, 10, 11]

code_classifications = {
    "PT0211": story_classifications,
    "PT0221": news_classifications,
    "PT0222": news_classifications,
    "PT0311": story_classifications,
    "PT0312": story_classifications,
    "PT0321": news_classifications
}

detail_codes_dict = {
    "PT01": ["PT0111", "PT0112", "PT0121"],
    "PT02": ["PT0211", "PT0221", "PT0222"],
    "PT03": ["PT0311", "PT0312", "PT0321"]
}


def calculate_age(birth_date: str) -> int:
    from datetime import datetime
    birth_date = datetime.strptime(birth_date, "%Y-%m-%d")
    today = datetime.today()
    age = today.year - birth_date.year - ((today.month, today.day) < (birth_date.month, birth_date.day))
    return age


def calculate_user_level(age: int) -> int:
    if 6 <= age <= 12:
        return age - 5  # 만 6세 -> 1레벨, 만 12세 -> 7레벨
    elif age < 6:
        return 1
    else:
        return 7


@router.get("/test/level")
async def get_level_test(user_id: Optional[str] = Header(None, alias="X-User-Id")):
    if not user_id:
        raise HTTPException(status_code=400, detail="유저ID가 없습니다.")

    # try:
    #     # 사용자 정보 API 호출
    #     async with httpx.AsyncClient() as client:
    #         response = await client.get(f"http://j11a506.p.ssafy.io:8082/api/users/{user_id}")
    #
    #     if response.status_code != 200:
    #         raise HTTPException(status_code=response.status_code, detail="사용자 정보를 불러올 수 없습니다.")
    #
    #     user_data = response.json()
    #     birth_date = user_data.get("birth")
    #
    #     if not birth_date:
    #         raise HTTPException(status_code=400, detail="사용자 출생일 정보가 없습니다.")
    #     # 출생일을 기준으로 나이 계산
    #     age = calculate_age(birth_date)
    #     # 나이를 기반으로 사용자 레벨 계산
    #     user_level = calculate_user_level(age)
    #
    # except httpx.RequestError as exc:
    #     raise HTTPException(status_code=500, detail=f"사용자 정보를 불러오는 중 오류가 발생했습니다: {exc}")
    # except ValueError as e:
    #     raise HTTPException(status_code=400, detail=str(e))

    selected_problems = []

    # 각 대유형별로 문제 선택
    for pt_type, detail_codes in detail_codes_dict.items():
        # 세부유형 순서를 랜덤하게 섞어 어떤 세부유형에서 문제를 가져올지 결정
        shuffled_detail_codes = detail_codes.copy()
        random.shuffle(shuffled_detail_codes)

        print(shuffled_detail_codes)
        # 첫 두 세부유형에서 2개씩, 마지막 세부유형에서 1개를 가져옴
        problem_counts = [2, 2, 1]

        for i, count in enumerate(problem_counts):
            detail_code = shuffled_detail_codes[i]  # 랜덤으로 섞인 세부유형에서 가져오기
            fetched_problems = get_random_problems_by_code_and_level(
                detail_code=detail_code,
                level=6,  # 나중에 user_level로
                limit=count
            )

            # fetched_problems가 MongoDB에서 가져온 경우 ObjectId를 문자열로 변환
            for problem in fetched_problems:
                if "_id" in problem:  # '_id' 필드가 있을 경우
                    problem["_id"] = str(problem["_id"])  # ObjectId를 문자열로 변환

            selected_problems.extend(fetched_problems)

    # 총 15문제가 선택되었는지 확인
    if len(selected_problems) != 15:
        raise HTTPException(
            status_code=500,
            detail="문제 선택 과정에서 예상치 못한 오류가 발생했습니다."
        )

    return selected_problems


@router.get("/test/general")
async def get_general_test(user_id: Optional[str] = Header(None, alias="X-User-Id")):
    if not user_id:
        raise HTTPException(status_code=400, detail="유저ID가 없습니다.")

    # try:
    #     # 사용자 정보 API 호출
    #     async with httpx.AsyncClient() as client:
    #         response = await client.get(f"http://j11a506.p.ssafy.io:8082/api/users/{user_id}")
    #
    #     if response.status_code != 200:
    #         raise HTTPException(status_code=response.status_code, detail="사용자 정보를 불러올 수 없습니다.")
    #
    #     user_data = response.json()
    #     birth_date = user_data.get("birth")
    #
    #     if not birth_date:
    #         raise HTTPException(status_code=400, detail="사용자 출생일 정보가 없습니다.")
    #     # 출생일을 기준으로 나이 계산
    #     age = calculate_age(birth_date)
    #     # 나이를 기반으로 사용자 레벨 계산
    #     user_level = calculate_user_level(age)
    #
    # except httpx.RequestError as exc:
    #     raise HTTPException(status_code=500, detail=f"사용자 정보를 불러오는 중 오류가 발생했습니다: {exc}")
    # except ValueError as e:
    #     raise HTTPException(status_code=400, detail=str(e))

    selected_problems = []
    type_index = [0, 1, 2]
    level_offsets = [-1, 0, 0, 1, 1]  # 난이도 조정
    user_level = 5

    #3개 PT01 PT02 PT03
    for pt_type, detail_codes in detail_codes_dict.items():
        # if 유형레벨 = 1
        #     level_offsets = [0, 0, 0, 1, 1]
        # elif 유형레벨 = 7
        #     level_offsets = [-1, 0, 0, 0, 0]

        detail_types = [0, 1, 2]
        detail_types.extend(random.choices(type_index, k=2))

        # 세부 유형 배열을 랜덤으로 섞기
        random.shuffle(level_offsets)

        # 인덱스와 레벨 배열 매칭
        indices = list(range(len(detail_types)))
        # print(indices)
        print(level_offsets)
        print(detail_types)

        #5개
        idx = 0
        for i in indices:
            detail_code = detail_codes_dict[pt_type][detail_types[i]]  # PT01 대유형에서 detail_code 선택
            level = user_level + level_offsets[i]  # 현재 레벨 매칭
            fetched_problem = None

            if idx < 3:
                fetched_problem = get_random_problems_by_code_and_level(
                    detail_code=detail_code,
                    level=level,
                    limit=1
                )
                idx = idx + 1
            else:
                if pt_type == "PT01":
                    # 문제 가져오기
                    fetched_problem = get_random_problems_by_code_and_level(
                        detail_code=detail_code,
                        level=level,
                        limit=1
                    )
                else:
                    top_classifications = top_n_classification(2, get_wrong_status(int(user_id)))
                    for classification in top_classifications:
                        correct_ids = get_correct_ids(int(user_id))
                        wrong_ids = get_wrong_ids(int(user_id))

                        fetched_problem = get_random_problems_by_log(
                            detail_code=classification[1],
                            level=level,
                            classification=classification[0],
                            correct_ids=correct_ids,
                            wrong_ids=wrong_ids,
                            vector=classification[3],
                            num = 1
                        )

            selected_problems.append(fetched_problem)

    # print(selected_problems)
    # 총 15문제가 선택되었는지 확인
    if len(selected_problems) != 15:
        raise HTTPException(
            status_code=500,
            detail="문제 선택 과정에서 예상치 못한 오류가 발생했습니다."
        )

    return selected_problems

#10개 가져오기
@router.get("/type")
async def get_type_test(user_id: Optional[str] = Header(None, alias="X-User-Id")):
    if not user_id:
        raise HTTPException(status_code=400, detail="유저ID가 없습니다.")

    selected_problems = []
    # 초기 값 설정
    type_index = [0, 1, 2]

    levels = [-1, 0, 1]  # 레벨 범위

    i = 0
    for pt_type, detail_codes in detail_codes_dict.items():
        # if 유형레벨 = 1
        #     levels = [0, 1]
        # elif 유형레벨 = 7
        #     levels = [-1, 0]

        type_counts = [3, 3, 4]  # 각 항목의 개수를 지정, 총합이 10

        # 랜덤한 세부 유형 배열 생성
        detail_types = []
        detail_levels = []
        for detail_type, count in zip([0, 1, 2], type_counts):
            detail_types.extend([detail_type] * count)  # 각 세부 유형을 count만큼 추가
        detail_levels.extend(random.choices(levels, k=10))

        # 인덱스와 레벨 배열 매칭
        indices = list(range(len(detail_types)))
        # print(indices)
        print(detail_levels)
        print("detail_types ", detail_types)

        idx = 0
        for detail_code in detail_codes:
            #10개
            if pt_type == "PT01":
                # 문제 가져오기
                fetched_problems = get_random_problems_by_code_and_level(
                    detail_code=detail_code,
                    level=5 + detail_levels[idx],
                    limit=type_counts[idx]
                )
            #20개
            else:
                top_classifications = top_n_classification(type_counts[i], get_wrong_status(int(user_id)))

                pt_detail_classifications = [item for item in top_classifications if item[1].startswith(detail_code)]
                print(f"pt_detail {detail_code}", pt_detail_classifications)

                correct_ids = get_correct_ids(int(user_id))
                wrong_ids = get_wrong_ids(int(user_id))

                #틀린 기록 없음 랜덤 추천
                if len(pt_detail_classifications) == 0:
                    fetched_problems = get_random_problems_by_code_and_level(
                        detail_code=detail_code,
                        level=5 + detail_levels[idx],
                        limit=type_counts[idx]
                    )
                #틀린 기록 있음
                else:
                    fetched_problems = get_random_problems_by_log(
                        detail_code=detail_code,
                        level=5 + detail_levels[idx],
                        classification=pt_detail_classifications[0][0],
                        correct_ids=correct_ids,
                        wrong_ids=wrong_ids,
                        vector=pt_detail_classifications[0][3],
                        num=type_counts[idx]
                    )
            selected_problems.extend(fetched_problems)
            print("detail_code", detail_code, "num", len(selected_problems))

            idx = idx + 1

    print(len(selected_problems))
    if len(selected_problems) != 30:
        raise HTTPException(
            status_code=500,
            detail="문제 선택 과정에서 예상치 못한 오류가 발생했습니다."
        )

    return selected_problems


@router.get("/similar")
async def get_level_test(problem_id: str, user_id: Optional[str] = Header(None, alias="X-User-Id")):
    if not user_id:
        raise HTTPException(status_code=400, detail="유저ID가 없습니다.")

    find_problem = get_problem_by_id(problem_id)

    if (find_problem['problemTypeCode'] == "PT01"):
        return get_problems_by_level_and_type(find_problem['problemLevelCode'], find_problem['problemTypeDetailCode'],
                                              problem_id)

    else:
        return get_similar(find_problem['problemLevelCode'], find_problem['problemTypeDetailCode'],
                           find_problem['vector'], problem_id)


def get_correct_ids(user_id: int):
    status = get_correct_sevendays(user_id)

    problem_ids = []

    for document in status:
        if 'problem' in document and document['problem'] is not None:
            if '_id' in document['problem']:
                # ObjectId를 문자열로 변환
                problem_id = str(document['problem']['_id'])
                problem_ids.append(problem_id)  # 문제 ID 추가

    return problem_ids


def get_wrong_ids(user_id: int):
    status = get_wrong_sevendays(user_id)

    problem_ids = []

    for document in status:
        if 'problem' in document and document['problem'] is not None:
            if '_id' in document['problem']:
                # ObjectId를 문자열로 변환
                problem_id = str(document['problem']['_id'])
                problem_ids.append(problem_id)  # 문제 ID 추가

    return problem_ids


@router.get("/wrong")
def get_wrong_status(user_id: int):
    wrong_problem_ids = get_wrong_ids(user_id)

    problems = get_problem_by_ids(wrong_problem_ids)

    # classification_vectors를 중첩 defaultdict로 정의
    classification_vectors = defaultdict(lambda: defaultdict(list))

    for problem in problems:

        if (problem["problemTypeCode"] == "PT01"): continue

        # document에서 classification과 vector를 추출
        classification = problem["classification"]
        vector = problem["vector"]
        detailcode = problem["problemTypeDetailCode"]

        # classification_vectors에 추가
        classification_vectors[classification][detailcode].append(vector)

    # 각 classification에 대해 detailcode별 평균 벡터와 횟수 계산
    classification_avg_vectors = {}
    for classification, detail_codes in classification_vectors.items():
        classification_avg_vectors[classification] = {}
        for detailcode, vectors in detail_codes.items():
            # numpy를 사용하여 각 벡터 요소별 평균 계산
            avg_vector = np.mean(vectors, axis=0).tolist()
            count = len(vectors)  # 벡터의 개수 (횟수) 계산

            # 평균 벡터와 횟수를 함께 저장
            classification_avg_vectors[classification][detailcode] = {
                "average_vector": avg_vector,
                "count": count
            }

    return classification_avg_vectors


@router.get("/not_solve")
async def not_solve_problems():
    not_solve_problems = get_problems_not_solve(1)
    return not_solve_problems


def top_n_classification(n, map):
    result = []
    for ckey in map.keys():
        detail_code_map = map.get(ckey)
        for dkey in detail_code_map.keys():
            value = detail_code_map.get(dkey)
            count = value.get('count')
            avg_vector = value.get('average_vector')

            result.append((ckey, dkey, count, avg_vector))

    result_sorted = sorted(result, key=lambda x: x[2], reverse=True)

    return result_sorted[:n]
