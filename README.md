# CentralChatControl
uses HTTP to send text to multiple Minecraft instances

# Use cases
When you have for example 4 minecraft instaces with baritone it gets annoying to copy paste the same commands over to all of them. 
With this you only need to send it to one place. 


# How to run
You need FastApi to run this, the website is also served through this

## Install

### Server
```bash
pip install fastapi uvicorn[standard]
```

### Client
Drop the mod (in build/libs/) into your mods folder


## Run 
### Server
in the project dir, run:
```bash
cd CommandAndControl
uvicorn main:app
```
Open [http://127.0.0.1:8000/](http://127.0.0.1:8000/) to access the inteface.

### Client
`@c3` - help
`@c3 t` - run once
`@c3 c` - run continually

# BUGS

Runnig the client once without ther server will crash the client


# TODO

Make the client send health etc to be viewd in ther webview

