import os
import uuid
from typing import List

from botocore.exceptions import ClientError
from fastapi import APIRouter, Depends, HTTPException, UploadFile, File
from starlette.responses import JSONResponse

from models import ProblemsResponse, Problem, ProblemOption, ProblemLevel, ProblemType
from repositories import save_problem, get_user, get_all_problems, get_user_problem_logs, get_user_problem_status, get_problems_not_solve
from sqlalchemy.orm import Session
from db import get_maria_db
from boto3 import client
import dotenv

dotenv.load_dotenv()
router = APIRouter(prefix="/api/recommend", tags=["recommend"])

# 예시 데이터
example_problem = Problem(
    problemId=1,
    title="Sample Problem 1",
    content="This is a sample problem content.",
    problemOptions=[
        ProblemOption(problemOptionId=1, option="Option A"),
        ProblemOption(problemOptionId=2, option="Option B"),
        ProblemOption(problemOptionId=3, option="Option C"),
        ProblemOption(problemOptionId=4, option="Option D")
    ],
    solution="Sample Solution",
    problemLevel=ProblemLevel(problemLevelCode="L1", name="Level 1"),
    problemType=ProblemType(
        problemTypeCode="T1",
        problemTypeDetailCode="T1-D1",
        name="Type 1"
    )
)


@router.get("/test/level", response_model=ProblemsResponse)
async def get_level_test():
    # 더미 데이터 생성
    return get_all_problems()


@router.get("/test/general", response_model=ProblemsResponse)
async def get_level_test():
    # 더미 데이터 생성
    return get_all_problems()


@router.get("/type", response_model=ProblemsResponse)
async def get_level_test():
    # 더미 데이터 생성
    return get_all_problems()


@router.get("/similar", response_model=ProblemsResponse)
async def get_level_test():
    # 더미 데이터 생성
    return get_all_problems()


@router.post("/make")
async def make_problem():
    save_problem(example_problem)


@router.get("/logs")
async def get_logs():
    logs = get_user_problem_logs(1)
    print(logs)
    return "logs hello"


@router.get("/status")
async def get_status():
    status = get_user_problem_status(1)
    print(status)
    return "status hello"


@router.get("/users")
async def get_user_problem(user_id: int, db: Session = Depends(get_maria_db)):
    print("user_id", user_id)
    user = get_user(db, 1)
    print("user", user)
    if user is None:
        raise HTTPException(status_code=404, detail="User not found")
    return user

@router.get("/not_solve")
async def not_solve_problems():
    not_solve_problems = get_problems_not_solve(1)
    return "not_solve_problems"


s3_client = client(
    "s3",
    aws_access_key_id=os.getenv("CREDENTIALS_ACCESS_KEY"),
    aws_secret_access_key=os.getenv("CREDENTIALS_SECRET_KEY"),
    region_name=os.getenv("S3_REGION"),
)

# 이미지를 저장할 디렉토리 경로
IMAGE_DIR = "./images"

@router.post("/upload_image")
async def upload_image(files: List[UploadFile] = File(...)):
    saved_files = []

    # 'images' 디렉토리가 없으면 생성
    os.makedirs(IMAGE_DIR, exist_ok=True)

    try:
        for file in files:
            # 고유한 파일 이름 생성
            file_extension = os.path.splitext(file.filename)[1]
            unique_filename = f"{uuid.uuid4()}{file_extension}"

            # 임시로 로컬에 파일 저장
            file_path = os.path.join(IMAGE_DIR, unique_filename)
            with open(file_path, "wb") as buffer:
                buffer.write(await file.read())
            saved_files.append(unique_filename)

        # S3에 이미지 업로드
        image_urls = saveImageAtS3(saved_files)
        return JSONResponse(content={"message": "Images uploaded successfully", "urls": image_urls})
    except Exception as e:
        # 에러 발생 시 임시 파일 정리
        for filename in saved_files:
            try:
                os.remove(os.path.join(IMAGE_DIR, filename))
            except:
                pass
        return JSONResponse(content={"error": f"Upload failed: {str(e)}"}, status_code=500)

def saveImageAtS3(images):
    imageUrls = []

    for image in images:
        file_path = os.path.join(IMAGE_DIR, image)
        try:
            # 지정한 S3 버킷에 이미지 파일 저장
            s3_client.upload_file(
                file_path,  # 로컬의 이미지 파일 경로
                os.getenv("S3_BUCKET"),  # S3 버킷 이름
                f"images/{image}",  # S3에 저장할 경로
                ExtraArgs={'ContentType': 'image/jpeg'}  # 이미지 MIME 타입 설정 (jpg로 가정)
            )

            # S3에 저장한 이미지 파일의 URL 생성
            imageUrls.append(f"{os.getenv('S3_URL')}/images/{image}")

        except Exception as e:
            raise  # 상위 함수에서 처리하도록 예외를 다시 발생시킵니다.

        finally:
            # 성공 여부와 관계없이 로컬 임시 파일 삭제 시도
            try:
                os.remove(file_path)
            except:
                pass

    return imageUrls