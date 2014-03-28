from flask.ext.sqlalchemy import SQLAlchemy
from marshmallow import Serializer, fields

from . import db

class Event(db.Model):
    __tablename__ = 'events'
    id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String)
    datetime = db.Column(db.DateTime)
    venue_id = db.Column(db.Integer)
    public = db.Column(db.Boolean)
    creator_id = db.Column(db.Integer, db.ForeignKey('users.id'))
    #tags = db.Column(db.Integer)
    #participant_ids

class EventSerializer(Serializer):

    class Meta:
        fields = ('id', 'name', 'datetime', 'creator_id')