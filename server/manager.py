from flask.ext.script import Manager
from broapp import app, db, User, Event, Tag
from random import shuffle

import datetime
import random

manager = Manager(app)


def random_db_entry(cls):
	rand = random.randrange(0, db.session.query(cls).count()) 
	return db.session.query(cls)[rand]

def random_users():
	users = db.session.query(User).all()
	shuffle(users)
	rand = random.randrange(0, db.session.query(User).count()) 

	return users[:rand]


@manager.command
def reset():
	clean_db()
	seed()


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

	tags = ["test", "some", "fancy", "party"]

	users = [
		["chris", "some@mail.com", "123", "male"],
		["ommi", "ommi@gmail.com", "ommispw", "male"],
		["david", "me@home.at", "somepw", "male"]
	]

	events = [
		["GreenSheep Energy Drinks Promo", 12321, True ],
		["Ommis gebfeier", 123245, True],
		["Test123", 0007, False]
	]

	for t in tags:
		tag = Tag(name=t)
		db.session.add(tag)

	for u in users:
		#username, email, sex, password
		user = User(u[0], u[1], u[3], u[2])
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
		event.tags.append(random_db_entry(Tag))
		for p in random_users():
			event.participant_ids.append(p)
		

		db.session.add(event)
	
	

if __name__ == "__main__":
    manager.run()