#!/bin/bash

virtualenv venv
. venv/bin/activate
pip install -r requirements.txt
python setup.py build
python setup.py install
./server.py