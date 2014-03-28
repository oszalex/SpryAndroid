# manage.py

from flask.ext.script import Manager
from models import db, User, Event
from broapp import app

import os

manager = Manager(app)

@manager.command
def hello():
    print "hello"

@manager.command
def clean_db():
	#delete entries
	User.query.delete()
	Event.query.delete()

	#delete tables
	for tbl in reversed(db.MetaData().sorted_tables):
		engine.execute(tbl.delete())


@manager.command
def seed():

	users = [
		["chris", "something"],
		["ommi", "ommispw"],
		["david", "somepw"]
	]

	events = [
		[],
		[],
		[]
	]

	for u in users:
		user = User(username=u[0], password=u[1])
		db.session.add(user)

	for e in events:
		event = Event()
		db.session.add(event)
	
	db.session.commit()

if __name__ == "__main__":
    manager.run()