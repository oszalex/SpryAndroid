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


from flask import Flask, abort, request, jsonify, g, url_for

from models import db, User
from controller import auth
from controller import *
import logging

app = Flask(__name__)

# setup logging
    
file_handler = logging.FileHandler("/tmp/broapp.log")
file_handler.setLevel(logging.DEBUG)
app.logger.addHandler(file_handler)

file_handler.setFormatter(logging.Formatter(
    '%(asctime)s %(levelname)s: %(message)s '
    '[in %(pathname)s:%(lineno)d]'
))

app.logger.warning("getBro started")

# setup database

db.init_app(app)
app.config['SECRET_KEY'] = 'the quick brown fox jumps over the lazy dog'
app.config['SQLALCHEMY_DATABASE_URI'] = 'mysql://bro:somefancypassword@api.getbro.com/broapp'
app.config['SQLALCHEMY_COMMIT_ON_TEARDOWN'] = True

with app.app_context():
        # Extensions like Flask-SQLAlchemy now know what the "current" app
        # is while within this block. Therefore, you can now run........
        db.create_all()
        g.user = None

@auth.verify_password
def verify_password(username, password):
    user = User.query.filter_by(username = username).first()

    if user is None:
      app.logger.warning('user %s not found' % (username))
      return False

    if not user or not user.check_password(password):
        app.logger.warning('password %s is invalid for user %s' % (password, username))
        return False

    app.logger.debug('user %s: sucessfully logged in' % (username))
    g.user = user
    return True



@app.route('/')
def hello_bro():
  return jsonify({"data": "Hello Bro!" })

# register controller
app.register_blueprint(autocomplete.autocomplete, url_prefix='/ac')
app.register_blueprint(info.info, url_prefix='/info')
app.register_blueprint(events.events, url_prefix='/events')
app.register_blueprint(users.users, url_prefix='/users')
app.register_blueprint(memberarea.mybro, url_prefix='/my')
app.register_blueprint(authentication.broauth, url_prefix='/auth')
app.register_blueprint(logviewer.logger, url_prefix='/logs')