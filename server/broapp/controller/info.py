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
from documentation import auto

info = Blueprint('info', __name__)

@info.route("/")
@auto.doc("public")
def hello():
	return "Hello Bro!"


@info.route("/version")
@auto.doc("public")
def version():
	"""Returns the current API version"""
	return jsonify({"version": subprocess.check_output(["git", "describe", "--tag"])})

