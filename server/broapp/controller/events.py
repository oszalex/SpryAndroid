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
from ..models import Event, EventSerializer, EventStateSerializer, EventFactory
from . import auth, errormsg

events = Blueprint('events', __name__)


@events.route("/")
@auth.login_required
def get_events():
    events = Event.query.filter_by(public=True).all()
    serialized = EventSerializer(events, many=True).data
    return jsonify({ "data" : serialized } )


@events.route('/', methods=["PUT", "POST"])
def insert_event():
    event = EventFactory.fromJson(request.get_json(force=True))

    db.session.add(event)
    db.session.commit()
    
    return jsonify({"data": EventSerializer(event).data})


@events.route("/<int:event_id>")
def get_event(event_id):
    event = Event.query.get(event_id)

    if event is not None:

        ##
        # access allowed if:
        #  - ) user is creator
        #  - ) user is participant
        #  - ) event is public
        ##

        if (event.public or
            (hasattr(g, 'user') and g.user is not None and g.user.id is event.creator_id) or
            (hasattr(g, 'user') and g.user is not None and g.user.id in event.participant_ids)):
                return jsonify({"data": EventSerializer(event).data})
        else:
            return errormsg("access forbidden", 403)

    else:
        return errormsg("There is no such an event for you, dear guest.", 404)


@events.route("/<int:event_id>", methods=['DELETE'])
@auth.login_required
def remove_event(event_id):
    event = Event.query.get(event_id)

    if event is not None:
        if g.user.id == event.creator_id:
            db.session.delete(event)
            abort(204)
        else:
            errormsg("access forbidden", 403)
    else:
        return errormsg("There is no such an event for you, dear guest.", 404)
