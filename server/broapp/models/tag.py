from flask.ext.sqlalchemy import SQLAlchemy
from marshmallow import Serializer

from . import db

class Tag(db.Model):
	__tablename__ = 'tags'
	id = db.Column(db.Integer, primary_key=True)
	name = db.Column(db.String)

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