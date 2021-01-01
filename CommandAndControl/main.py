from typing import Optional
from pydantic import BaseModel
from fastapi import FastAPI
from fastapi.responses import HTMLResponse
from fastapi.middleware.cors import CORSMiddleware
import requests

# run with > uvicorn main:app --reload

app = FastAPI()

origins = [
    "*"
]

app.add_middleware(
    CORSMiddleware,
    allow_origins=origins,
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

connected_clients = []
jobs = [] # jobid = len(jobs)
jobid = 0

class login(BaseModel):
    name: str

class job(BaseModel):
    command: str

class jobindex(BaseModel):
    i: int

@app.post("/id")
def get_id(name:  login):
    r = requests.get("https://api.mojang.com/users/profiles/minecraft/" + name.name)
    if r.status_code != 200:
        return "ec561538f3fd461daff5086b22154bce"
    
    return r.json()["id"]

@app.post("/login")
async def register(item: login):
    if item.name not in connected_clients:
        connected_clients.append(item.name)
        return {"message": "Registerd!"}
    return {"message": "Already registerd!"}
    
@app.get("/login")
async def get_registerd():
    return connected_clients

@app.delete("/login")
async def delete_user(index: jobindex):
    global connected_clients
    if index.i == -1:
        connected_clients = []
        return "Users removed"
    elif 0 <= index.i < len(connected_clients):
        t = connected_clients[index.i]
        del connected_clients[index.i]
        return f'User "{t}" removed'
    return "User not in range"

@app.get("/jobs")
async def get_jobs():
    return {"jobs": jobs, "jobid": len(jobs)}

@app.post("/jobs")
async def add_job(item: job):
    jobs.append(item.command)
    return "Job added!"

@app.delete("/jobs")
async def delete_job(index: jobindex):
    global jobs
    if index.i == -1:
        jobs = []
        return "Jobs removed"
    elif 0 <= index.i < len(jobs):
        t = jobs[index.i]
        del jobs[index.i]
        return f'Job "{t}" removed'
    return "Job not in range"

@app.get("/", response_class=HTMLResponse)
def read_root():
    return open("index.html").read()


# @app.post("/")
# def post_root():
#     return {"Hello": "World"}


# @app.get("/items/{item_id}")
# def read_item(item_id: int, q: Optional[str] = None):
#     return {"item_id": item_id, "q": q}
