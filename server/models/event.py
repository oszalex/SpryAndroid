from flask.ext.sqlalchemy import SQLAlchemy
from marshmallow import Serializer, fields

db = SQLAlchemy()

class Event(db.Model):
    __tablename__ = 'events'
    id = db.Column(db.Integer, primary_key=True)




class EventSerializer(Serializer):

    class Meta:
        fields = ('id')