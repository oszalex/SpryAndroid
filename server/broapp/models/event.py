from flask.ext.sqlalchemy import SQLAlchemy
from marshmallow import Serializer, fields

import datetime as dt

from . import db, TagSerializer, UserSerializer, ModelValidator

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
    participant_ids = db.relationship('Invitation')


class EventFactory(object):
    @staticmethod
    def fromJson(json_obj):

        ModelValidator.json('event', json_obj)

        event = Event(
            name=json_obj["name"],
            datetime=dt.datetime.strptime(json_obj["datetime"],'%Y-%m-%dT%H:%M:%S.%fZ'),
            venue_id = json_obj["venue_id"],
            public = json_obj["public"],
            creator_id = json_obj["creator_id"]
            )
        return event


class EventSerializer(Serializer):
	tags = fields.Nested(TagSerializer, many=True)
	participant_ids = fields.Nested(UserSerializer, only='id', many=True)

	class Meta:
		fields = ('id', 'name', 'venue_id', 'datetime', 'creator_id', 'tags', 'participant_ids', 'public')