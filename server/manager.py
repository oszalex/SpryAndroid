# manage.py

from flask.ext.script import Manager
from models import db, User, Event
from broapp import app

import datetime
import random

manager = Manager(app)


def random_db_entry(cls):
	rand = random.randrange(0, db.session.query(cls).count()) 
	return db.session.query(cls)[rand]


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
		["GreenSheep Energy Drinks Promo", 12321, True ],
		["Ommis gebfeier", 123245, True],
		["Test123", 0007, False]
	]

	for u in users:
		user = User(
			username=u[0], 
			password=u[1]
			)
		db.session.add(user)

	#add users
	db.session.commit()

	for e in events:
		event = Event(
			name=e[0], 
			venue_id=e[1], 
			public=e[2], 
			datetime=datetime.datetime.utcnow(), 
			creator_id=random_db_entry(User).id
			)
		db.session.add(event)
	
	

if __name__ == "__main__":
    manager.run()