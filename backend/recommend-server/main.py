from fastapi import FastAPI
from routers import recommend_router, user_router

app = FastAPI()

# 라우터 등록
app.include_router(recommend_router)
app.include_router(user_router)
