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
from ..models import Event, EventSerializer, EventStateSerializer

mybro = Blueprint('mybro', __name__)



@mybro.route("/followers")
@auth.login_required
def get_my_followers():
    return ""

@mybro.route("/followings")
@auth.login_required
def get_my_followings():
    return ""

@mybro.route("/events")
@auth.login_required
def get_my_events():
    events = Event.query.filter_by(public=True).all()

    events.extend(Event.query.filter_by(public=False, creator_id=g.user.id).all())

    #TODO all where user is participant

    #Sort events
    events.sort(key=lambda x: x.datetime)

    serialized = EventStateSerializer(events, many=True, context={'user': g.user}).data

    #for e in events:
    #    
    #    if state is not None:
    #        serialized["status"] = state.attending

    return jsonify({ "data" : serialized } )



@mybro.route("/events/<int:event_id>")
@auth.login_required
def get_my_event(event_id):
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

