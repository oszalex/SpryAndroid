from flask import Flask, abort, request, jsonify, g, url_for
from flask.ext.sqlalchemy import SQLAlchemy
from flask.ext.httpauth import HTTPBasicAuth
from passlib.apps import custom_app_context as pwd_context

from models import db
from models.tag import Tag
from models.user import User, UserSerializer
from models.event import Event, EventSerializer, EventStateSerializer, EventFactory
from models.invitation import Invitation

import json

from controller import *

app = Flask(__name__)

db.init_app(app)
app.config['SECRET_KEY'] = 'the quick brown fox jumps over the lazy dog'
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///db.sqlite'
app.config['SQLALCHEMY_COMMIT_ON_TEARDOWN'] = True

auth = HTTPBasicAuth()

with app.app_context():
        # Extensions like Flask-SQLAlchemy now know what the "current" app
        # is while within this block. Therefore, you can now run........
        db.create_all()
        g.user = None






# register controller

app.register_blueprint(autocomplete.autocomplete, url_prefix='/ac')
app.register_blueprint(info.info, url_prefix='/info')


@auth.verify_password
def verify_password(username, password):
    user = User.query.filter_by(username = username).first()

    if not user or not user.check_password(password):
        app.logger.warning('password %s is invalid for user %s' % (password, user.username))
        return False

    app.logger.debug('user %s: sucessfully logged in' % (user.username))
    g.user = user
    return True


def errormsg(msg, code):
    return jsonify({"error": msg}), code




'''
Authentication Endpoints
'''

@app.route("/login")
@auth.login_required
def login():
    return "Hello %s!" % g.user.username

@app.route("/logout")
@auth.login_required
def logout():
	abort(401)







'''
My assets/data
'''

@app.route("/my/followers")
@auth.login_required
def get_my_followers():
    return ""

@app.route("/my/followings")
@auth.login_required
def get_my_followings():
    return ""

@app.route("/my/events")
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



@app.route("/my/events/<int:event_id>")
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





'''
#<<NOT PART OF BETA-PHASE>>
@app.route('/register', methods = ['POST'])
def new_user():
    username = request.json.get('username')
    password = request.json.get('password')
    if username is None or password is None:
        abort(400) # missing arguments
    if User.query.filter_by(username = username).first() is not None:
        abort(400) # existing user
    user = User(username = username)
    user.hash_password(password)
    db.session.add(user)
    db.session.commit()
    return jsonify({ 'username': user.username }), 201, {'Location': url_for('get_user', id = user.id, _external = True)}
'''


'''
users
'''
@app.route("/users")
@auth.login_required
def get_users():
    return jsonify({"data": UserSerializer(User.query.all(), many=True).data})


@app.route("/users/<int:user_id>")
@auth.login_required
def get_user(user_id):
    user = User.query.get(user_id)

    if user is not None:
        return jsonify({"data": UserSerializer(user).data})
    else:
        return errormsg("There is no such a user for you, dear guest.", 404)



@app.route("/users/me")
@auth.login_required
def get_current_user():

    if g.user is not None:
        return jsonify({"data": UserSerializer(g.user).data})
    else:
        return errormsg("You're not logged in, bro", 404)




'''
events
'''

@app.route("/events")
def get_events():
    events = Event.query.filter_by(public=True).all()
    serialized = EventSerializer(events, many=True).data
    return jsonify({ "data" : serialized } )


@app.route('/events', methods=["PUT", "POST"])
def insert_event():
    event = EventFactory.fromJson(request.get_json(force=True))

    db.session.add(event)
    db.session.commit()
    
    return jsonify({"data": EventSerializer(event).data})



@app.route("/events/<int:event_id>")
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


@app.route("/events/<int:event_id>", methods=['DELETE'])
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





'''''
Tags
'''''
@app.route("/tags/<tag_name>")
def get_events_from_tag(tag_name):
    tag = Tag.query.filter_by(name=tag_name).first()

    if tag is not None:
        return jsonify({"data": EventSerializer(tag.events, many=True).data})

    else:
        return jsonify({data:[]})



if __name__ == "__main__":
    app.run()
