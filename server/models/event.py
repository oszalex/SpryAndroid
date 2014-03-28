from flask.ext.sqlalchemy import SQLAlchemy
from marshmallow import Serializer, fields

from . import db, TagSerializer

event_tags = db.Table('event_tags',
	    db.Column('tag_id', db.Integer, db.ForeignKey('tags.id')),
	    db.Column('event_id', db.Integer, db.ForeignKey('events.id'))
	)

class Event(db.Model):
    __tablename__ = 'events'
    id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String)
    datetime = db.Column(db.DateTime)
    venue_id = db.Column(db.Integer)
    public = db.Column(db.Boolean)
    creator_id = db.Column(db.Integer, db.ForeignKey('users.id'))
    tags = db.relationship('Tag', secondary=event_tags,
        backref=db.backref('events', lazy='dynamic'))

    #participant_ids



class EventSerializer(Serializer):
	tags = fields.Nested(TagSerializer, many=True)

	class Meta:
		fields = ('id', 'name', 'datetime', 'creator_id', 'tags')