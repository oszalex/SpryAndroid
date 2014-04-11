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

from flask import Blueprint, jsonify, g
from ..models import User, UserSerializer
from . import auth, errormsg

users = Blueprint('users', __name__)

@users.route("/")
@auth.login_required
def get_users():
    return jsonify({"data": UserSerializer(User.query.all(), many=True).data})


@users.route("/<int:user_id>")
@auth.login_required
def get_user(user_id):
    user = User.query.get(user_id)

    if user is not None:
        return jsonify({"data": UserSerializer(user).data})
    else:
        return errormsg("There is no such a user for you, dear guest.", 404)



@users.route("/me")
@auth.login_required
def get_current_user():
  return jsonify({"data": UserSerializer(g.user).data})
