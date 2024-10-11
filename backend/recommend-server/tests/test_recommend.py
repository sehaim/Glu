from fastapi.testclient import TestClient
from main import app

client = TestClient(app)

# def test_get_type_problem_set():
#     response = client.get("/api/recommend/type", headers={"X-User-Id": "64"})
#     assert response.status_code == 200
#     assert len(response.json()) == 30

def test_get_general_problem_set():
    response = client.get("/api/recommend/type", headers={"X-User-Id": "64"})
    assert response.status_code == 200
    assert len(response.json()) == 30
