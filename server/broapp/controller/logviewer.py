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

from flask import Blueprint, g, Response
from .. import auth

logger = Blueprint('logviewer', __name__)

@logger.route("/")
@auth.login_required
def login():
	f = open("/tmp/broapp.log", "r")
	#f = open(g.LOGGING_DIR + "/../logs/broapp.log", "r")
	return Response(f.read() , mimetype="text/plain;charset=UTF-8")

