# README

## Install & Run

create a virtual environment

> virtualenv venv

active it

> . venv/bin/activate  

install all dependencies

> python setup.py build  
> python setup.py install  

run the server

> ./server.py

Now you can open `https://127.0.0.1:8080` with your favourite browser.


## shut it down

stop the process and then deactive the environment

> deactivate

## Commands

`python manager.py clean_db` deletes all tables

`python manager.py seed` seed db with dummy values

`python manager.py reset` clear and seed db with dummy values
 
#Links

Hash: http://stackoverflow.com/questions/800685/which-cryptographic-hash-function-should-i-choose
Flask: http://pycoder.net/bospy/presentation.html
