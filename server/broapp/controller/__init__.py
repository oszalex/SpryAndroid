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

from flask.ext.httpauth import HTTPBasicAuth
from flask import jsonify
from math import ceil

auth = HTTPBasicAuth()

EVENTS_PER_RESPONSE = 10
USERS_PER_RESONSE = 4
TAGS_PER_RESPONSE = 8

def errormsg(msg, code):
    return jsonify({"error": msg}), code


__all__ = ["autocomplete", "info", "events", "users", "memberarea", "authentication", "logviewer"]
