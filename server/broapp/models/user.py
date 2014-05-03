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

from . import db

import hashlib
import string
import random

followers = db.Table('followers',
    db.Column('follower_id', db.Integer, db.ForeignKey('users.id')),
    db.Column('followed_id', db.Integer, db.ForeignKey('users.id'))
)

class User(db.Model):
    __tablename__ = 'users'
    id = db.Column(db.Integer, primary_key=True)
    username = db.Column(db.String(32), index=True, nullable=False, unique=True)
    email = db.Column(db.String(32), index=True, nullable=False)
    sex = db.Column(db.Enum('male', 'female'))
    salt = db.Column(db.String(6), nullable=False)
    password = db.Column(db.String(128), nullable=False)
    followed = db.relationship('User', 
        secondary = followers, 
        primaryjoin = (followers.c.follower_id == id), 
        secondaryjoin = (followers.c.followed_id == id), 
        backref = db.backref('followers', lazy = 'dynamic'), 
        lazy = 'dynamic')

    def __init__(self, username, email, sex, password):
        self.username = username
        self.email = email.lower()
        self.sex = sex
        self.salt = self.salt_generator()
        self.set_password(password)

    def salt_generator(self, size=6, chars=string.digits+string.letters):
        return ''.join(random.choice(chars) for _ in range(size))

    def set_password(self, password):
        self.password = hashlib.sha512(password + self.salt).hexdigest()

    def check_password(self, password):
        calc = hashlib.sha512(password + self.salt).hexdigest()
        return (calc == self.password)

    def follow(self, user):
        if not self.is_following(user):
            self.followed.append(user)
            return self

    def unfollow(self, user):
        if self.is_following(user):
            self.followed.remove(user)
            return self

    def is_following(self, user):
        return self.followed.filter(followers.c.followed_id == user.id).count() > 0


class UserSerializer(Serializer):
  #followed = fields.Nested('self',  many=True)
  followed = fields.Method("get_followed_usernames")

  followers = fields.Method("get_follower_usernames")

  def get_follower_usernames(self, obj):
    user_usernames = []

    for f in obj.followers:
      user_usernames.append(f.username)

    return user_usernames

  def get_followed_usernames(self, obj):
    user_usernames = []

    for f in obj.followed:
      user_usernames.append(f.username)
      
    return user_usernames


    
  class Meta:  
    fields = ('id', 'username', 'sex', 'followers', 'followed')



