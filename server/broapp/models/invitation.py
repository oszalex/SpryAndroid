from . import db

class Invitation(db.Model):
    __tablename__ = 'event_participants'
    users_id = db.Column(db.Integer, db.ForeignKey('users.id'), primary_key=True)
    events_id = db.Column(db.Integer, db.ForeignKey('events.id'), primary_key=True)
    attending = db.Column(db.Enum('undefined', 'yes', 'maybe', 'no'), default="undefined")
    user = db.relationship('User', backref=db.backref('invitations', cascade='delete'))