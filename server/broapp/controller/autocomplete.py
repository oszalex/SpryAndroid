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
from .. import db
from ..models import User, UserSerializer, Tag, TagSerializer, Event, EventSerializer

autocomplete = Blueprint('autocomplete', __name__)


@autocomplete.route("/users/<regex>")
def get_regex_user(regex):
    query = db.session.query(User).filter(User.username.like(regex + "%"))
    return jsonify({"data": UserSerializer(query.all(), many=True).data })


@autocomplete.route("/tags/<regex>")
def get_regex_tag(regex):
    query = db.session.query(Tag).filter(Tag.name.like(regex + "%"))
    return jsonify({"data": TagSerializer(query.all(), many=True).data })

@autocomplete.route("/events/<regex>")
def get_regex_event(regex):
    query = db.session.query(Event).filter(Event.name.like(regex + "%"))
    return jsonify({"data": EventSerializer(query.all(), many=True).data })
