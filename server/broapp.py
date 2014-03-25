from flask import Flask, abort, request, jsonify, g, url_for
from flask.ext.sqlalchemy import SQLAlchemy
from flask.ext.httpauth import HTTPBasicAuth
from passlib.apps import custom_app_context as pwd_context

# imort models
from models.user import db



app = Flask(__name__)

db.init_app(app)
app.config['SECRET_KEY'] = 'the quick brown fox jumps over the lazy dog'
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///db.sqlite'
app.config['SQLALCHEMY_COMMIT_ON_TEARDOWN'] = True

# DEBUG!
app.debug = True
auth = HTTPBasicAuth()

with app.app_context():
        # Extensions like Flask-SQLAlchemy now know what the "current" app
        # is while within this block. Therefore, you can now run........
        db.create_all()




@auth.get_password
def get_pw(username):
    if username in users:
        return users.get(username)
    return None

@auth.verify_password
def verify_password(username, password):
    user = User.query.filter_by(username = username).first()
    if not user or not user.verify_password(password):
        return False
    g.user = user
    return True





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
def login():
    return "Hello World!"

@app.route("/logout")
@auth.login_required
def logout():
	return ""

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
users
'''
@app.route("/users")
def get_users():

    users = User.query.all()

    output = []
    for user in users:
        row = {}

    for user in User.__table__.c:
        row[str(field)] = getattr(user, field, None)
        output.append(row)

    return jsonify(data=output)





if __name__ == "__main__":
    app.run()
