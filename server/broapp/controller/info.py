'''
             _   ____  _____   ____   
            | | |  _ \|  __ \ / __ \  
   __ _  ___| |_| |_) | |__) | |  | | 
  / _` |/ _ \ __|  _ <|  _  /| |  | | 
 | (_| |  __/ |_| |_) | | \ \| |__| | 
  \__, |\___|\__|____/|_|  \_\\____(_)
   __/ |                              
  |___/                                


'''

from flask import Blueprint, jsonify
import subprocess

info = Blueprint('info', __name__)

@info.route("/")
def hello():
	return "Hello Bro!"


@info.route("/version")
def version():
    return jsonify({"version": subprocess.check_output(["git", "describe", "--tag"])})

