from flask.ext.sqlalchemy import SQLAlchemy
from marshmallow import Serializer, fields

from . import db

import hashlib
import string
import random

class User(db.Model):
    __tablename__ = 'users'
    id = db.Column(db.Integer, primary_key=True)
    username = db.Column(db.String(32), index=True, nullable=False)
    email = db.Column(db.String(32), index=True, nullable=False)
    sex = db.Column(db.Enum('male', 'female'))
    salt = db.Column(db.String(6), nullable=False)
    password = db.Column(db.String(64), nullable=False)

    def __init__(self, username, email, sex, password):
        self.username = username
        self.email = email.lower()
        self.sex = sex
        self.salt = self.salt_generator()
        self.set_password(password)

    def salt_generator(self, size=6, chars=string.printable):
        return ''.join(random.choice(chars) for _ in range(size))

    def set_password(self, password):
        self.password = hashlib.sha512(password + self.salt).hexdigest()

    def check_password(self, password):
        calc = hashlib.sha512(password + self.salt).hexdigest()
        return (calc == self.password)


class UserSerializer(Serializer):

    class Meta:
        fields = ('id', 'username', 'sex')