from flask.ext.sqlalchemy import SQLAlchemy
from marshmallow import Serializer

from . import db

class Tag(db.Model):
	__tablename__ = 'tags'
	id = db.Column(db.Integer, primary_key=True)
	name = db.Column(db.String)


class TagSerializer(Serializer):
    class Meta:
        fields = ('id', 'name')