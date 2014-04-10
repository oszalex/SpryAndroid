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
from marshmallow import Serializer, fields

import datetime as dt
from dateutil import parser

from . import db, ModelValidator
from tag import Tag, TagSerializer
from user import User, UserSerializer
from invitation import Invitation


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
    public = db.Column(db.Boolean,unique=False, default=False)
    creator_id = db.Column(db.Integer, db.ForeignKey('users.id'))
    tags = db.relationship('Tag', secondary=event_tags,
        backref=db.backref('events', lazy='dynamic'))
    participant_ids = db.relationship('Invitation',
        backref=db.backref('events'))

    @staticmethod
    def get_or_create(**kwargs):
        instance = db.session.query(Event).filter_by(**kwargs).first()
        if instance is not None:
            return instance
        else:
            instance = Event(**kwargs)
            db.session.add(instance)
            return instance


class EventFactory(object):
    @staticmethod
    def fromJson(json_obj):

        ModelValidator.json('event', json_obj)

        event = Event(
            name =json_obj["name"],
            datetime=parser.parse(json_obj["datetime"]),
            venue_id = json_obj["venue_id"],
            creator_id = json_obj["creator_id"],
        )

        #create invitations
        for p in json_obj['participant_ids']:
            user = User.query.get(p)
            if user is not None:
                i = Invitation()
                i.user = user
                event.participant_ids.append(i)

        if 'public' in json_obj:
            event.public = json_obj["public"]

        if 'tags' in json_obj:
            for t in json_obj['tags']:
                tag = Tag.get_or_create(name = t)
                event.tags.append(tag)

        return event


class EventSerializer(Serializer):
	tags = fields.Nested(TagSerializer,only='name', many=True)
	#participant_ids = fields.Nested(UserSerializer, many=True)
	participant_ids = fields.Method("get_participant_ids")
	datetime = fields.Method("get_datetime")

	def get_participant_ids(self, obj):
		user_ids = []

		for invitation in obj.participant_ids:
			user_ids.append(invitation.user.id)
		return user_ids #UserSerializer(users, many=True).data

	def get_datetime(self, obj):
		return obj.datetime.__format__('%Y-%m-%dT%H:%M:%S.%f+0100')


	class Meta:
		fields = ('id', 'name', 'venue_id', 'datetime', 'creator_id', 'tags', 'participant_ids', 'public')


class EventStateSerializer(Serializer):
    tags = fields.Nested(TagSerializer,only='name', many=True)
    #participant_ids = fields.Nested(UserSerializer, many=True)
    participant_ids = fields.Method("get_participant_ids")
    datetime = fields.Method("get_datetime")
    status = fields.Function(lambda event, ctx: Invitation.query.filter_by(users_id=ctx['user'].id, events_id=event.id).first().attending)

    def get_participant_ids(self, obj):
        user_ids = []

        for invitation in obj.participant_ids:
            user_ids.append(invitation.user.id)
        return user_ids #UserSerializer(users, many=True).data

    def get_datetime(self, obj):
        return obj.datetime.__format__('%Y-%m-%dT%H:%M:%S.%f+0100')


    class Meta:
        fields = ('id', 'name', 'venue_id', 'datetime', 'creator_id', 'tags', 'participant_ids', 'public', 'status')