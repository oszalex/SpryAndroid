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
from . import USERS_PER_RESONSE
from flask.ext.sqlalchemy import BaseQuery
from documentation import auto

autocomplete = Blueprint('autocomplete', __name__)

@autocomplete.route("/users/<regex>", defaults={'page_num': 1})
@autocomplete.route("/users/<regex>/<int:page_num>")
@auto.doc("public")
def get_regex_user(regex, page_num):
    ''' find users with a name like <regex> '''
    query = db.session.query(User).filter(User.username.like(regex + "%"))
    #items = query.paginate(page_num, USERS_PER_RESONSE).items
    items.all()
    return jsonify({"data": UserSerializer(items, many=True).data })


@autocomplete.route("/tags/<regex>")
@auto.doc("public")
def get_regex_tag(regex):
    ''' find tags with a name like <regex> '''
    query = db.session.query(Tag).filter(Tag.name.like(regex + "%"))
    return jsonify({"data": TagSerializer(query.all(), many=True).data })

@autocomplete.route("/events/<regex>")
@auto.doc("public")
def get_regex_event(regex):
    ''' find events with a name like <regex> '''
    query = db.session.query(Event).filter(Event.name.like(regex + "%"))
    return jsonify({"data": EventSerializer(query.all(), many=True).data })
