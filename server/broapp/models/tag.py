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

from flask.ext.sqlalchemy import SQLAlchemy
from marshmallow import Serializer

from . import db

class Tag(db.Model):
	__tablename__ = 'tags'
	id = db.Column(db.Integer, primary_key=True)
	name = db.Column(db.String(50))

	@staticmethod
	def get_or_create(**kwargs):
		instance = db.session.query(Tag).filter_by(**kwargs).first()
		if instance is not None:
			return instance
		else:
			instance = Tag(**kwargs)
			db.session.add(instance)
			return instance


class TagSerializer(Serializer):
    class Meta:
        fields = ('id', 'name')