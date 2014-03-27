from flask.ext.sqlalchemy import SQLAlchemy

db = SQLAlchemy()

__all__ = ["event", "user"]


from user import User, UserSerializer
from event import Event, EventSerializer