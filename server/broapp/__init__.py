from flask import Flask, abort, request, jsonify, g, url_for
from flask.ext.sqlalchemy import SQLAlchemy
from flask.ext.httpauth import HTTPBasicAuth
from passlib.apps import custom_app_context as pwd_context

from models import db
from models.tag import Tag
from models.user import User, UserSerializer
from models.event import Event, EventSerializer, EventFactory

import json

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
Endpoints
'''

@app.route("/")
def hello():
	return "Hello Bro!"


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
    return jsonify({"users": UserSerializer(User.query.all(), many=True).data})


@app.route("/users/<int:user_id>")
def get_user(user_id):
    user = User.query.get(user_id)

    if user is not None:
        return jsonify({"user": UserSerializer(user).data})
    else:
        return errormsg("There is no such a user for you, dear guest.", 404)



@app.route("/users/me")
@auth.login_required
def get_current_user():

    if g.user is not None:
        return jsonify({"user": UserSerializer(g.user).data})
    else:
        return errormsg("You're not logged in, bro", 404)




'''
events
'''

@app.route("/events")
def get_events():
    events = Event.query.filter_by(public=True).all()

    if hasattr(g, 'user') and g.user is not None:
        events.append(Event.query.filter_by(public=False, creator_id=g.user.id).all())
        #TODO: add all, where g.user is participant

    #Sort events
    events.sort(key=lambda x: x.datetime)

    #add attending state

    return jsonify({"events": EventSerializer(events, many=True).data})


@app.route('/events', methods=["PUT", "POST"])
def insert_event():
    event = EventFactory.fromJson(request.get_json(force=True))

    db.session.add(event)
    
    return jsonify({"event": EventSerializer(event).data})



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
                return jsonify({"event": EventSerializer(event).data})
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



if __name__ == "__main__":
    app.run()
