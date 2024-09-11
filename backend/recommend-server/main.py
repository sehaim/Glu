from fastapi import FastAPI
from recommendRouter import router  # router.py에서 라우터 가져오기

app = FastAPI()

# 라우터 등록
app.include_router(router)