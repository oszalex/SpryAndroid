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


from user import User, UserSerializer
from tag import Tag, TagSerializer
from event import Event, EventSerializer, EventFactory
from invitation import Invitation

__all__ = ["event", "user", "tag", "invitation"]