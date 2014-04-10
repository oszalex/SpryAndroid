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
from . import auth
from ..models import Tag, EventSerializer

tags = Blueprint('tags', __name__)


@tags.route("/<tag_name>")
def get_events_from_tag(tag_name):
    tag = Tag.query.filter_by(name=tag_name).first()

    if tag is not None:
        return jsonify({"data": EventSerializer(tag.events, many=True).data})

    else:
        return jsonify({data:[]})



if __name__ == "__main__":
    app.run()
