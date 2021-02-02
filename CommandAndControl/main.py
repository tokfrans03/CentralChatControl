import enum
from os import name
from typing import Optional
from fastapi import FastAPI
from pydantic import BaseModel
from fastapi.responses import HTMLResponse
from fastapi.staticfiles import StaticFiles
from fastapi.middleware.cors import CORSMiddleware
import requests
import uvicorn
import zipfile
import shutil
import os


from distutils.dir_util import copy_tree


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

updateLog = []

class login(BaseModel):
    name: str
    health: int

class job(BaseModel):
    command: str

class jobindex(BaseModel):
    i: int

class string(BaseModel):
    name: str

@app.post("/id")
def get_id(name:  string):
    r = requests.get("https://api.mojang.com/users/profiles/minecraft/" + name.name)
    if r.status_code != 200:
        return "ec561538f3fd461daff5086b22154bce"
    
    return r.json()["id"]

@app.post("/login")
async def register(item: login):
    global connected_clients
    for i, client in enumerate(connected_clients):
        if client["name"] == item.name: # regged
            connected_clients[i] = { # update
                "name": item.name,
                "health": item.health
            }
            return {"message": "info updated!"}
    else:
        connected_clients.append({ # add
            "name": item.name,
            "health": item.health
        })
        return {"message": "Registerd!"}
    return {"message": "Registerd!"}

    
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
        t = connected_clients[index.i]["name"]
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

@app.get("/update")
def check_update():
    r = requests.get("https://raw.githubusercontent.com/tokfrans03/CentralChatControl/master/CommandAndControl/version")
    if r.text != open("version").read():
        return True
    return False

@app.post("/update")
async def update_all():
    global updateLog
    updatefiledir = "UpdateFiles"
    CaC = updatefiledir + "/CentralChatControl-master/CommandAndControl/"


    print("Starting...")
    print("Downloading...")
    r = requests.get("https://github.com/tokfrans03/CentralChatControl/archive/master.zip")
    print("Extracting...")
    with open("update.zip", "wb") as zip:
        zip.write(r.content)
    
    with zipfile.ZipFile("update.zip", "r") as zip:
        zip.extractall(updatefiledir)
    print("Moving...")

    folder = "webview/dist"

    for filename in os.listdir(folder):
        file_path = os.path.join(folder, filename)
        try:
            if os.path.isfile(file_path) or os.path.islink(file_path):
                os.unlink(file_path)
            elif os.path.isdir(file_path):
                shutil.rmtree(file_path)
        except Exception as e:
            print('Failed to delete %s. Reason: %s' % (file_path, e))

    copy_tree(CaC + "webview/dist", "webview/dist")
    os.rename(CaC + "main.py", "main.py")
    os.rename(CaC + "version", "version")

    print("Cleaing up...")
    shutil.rmtree(updatefiledir, ignore_errors=True)
    os.remove("update.zip")

    print("Done!")

    
        

@app.get("/", response_class=HTMLResponse)
def read_root():
    return open(f"{webroot}index.html").read()

webroot = "webview/dist/"
app.mount("/", StaticFiles(directory=webroot), name="static")


# @app.get("/index.html", response_class=HTMLResponse)
# def read_html():
#     return open(f"{webroot}index.html").read()

# @app.get("/js/{file}", response_class=HTMLResponse)
# def read_js(file: str):
#     return open(f"{webroot}js/{file}").read()

# @app.get("/css/{file}", response_class=HTMLResponse)
# def read_css(file: str):
#     return open(f"{webroot}css/{file}").read()


# @app.post("/")
# def post_root():
#     return {"Hello": "World"}


# @app.get("/items/{item_id}")
# def read_item(item_id: int, q: Optional[str] = None):
#     return {"item_id": item_id, "q": q}

if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=8000)