from flask.ext.sqlalchemy import SQLAlchemy
from jsonschema import validate
import os
import json


db = SQLAlchemy()

class ModelValidator(object):
	@staticmethod
	def json(scheme, input):
		current_parent_dir = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
		with open(os.path.join(current_parent_dir , 'schemes/%s.json' % scheme)) as data_file:    
			data = json.load(data_file)

			validate(input, data)


class Invitation(db.Model):
    __tablename__ = 'event_participants'
    users_id = db.Column(db.Integer, db.ForeignKey('users.id'), primary_key=True)
    events_id = db.Column(db.Integer, db.ForeignKey('events.id'), primary_key=True)
    attending = db.Column(db.Enum('undefined', 'yes', 'maybe', 'no'), default="undefined")
    user = db.relationship('User', backref=db.backref('invitations', cascade='delete'))

__all__ = ["event", "user", "tag", "invitation"]

