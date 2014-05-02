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

from . import db

class Invitation(db.Model):
    __tablename__ = 'event_participants'
    users_id = db.Column(db.Integer, db.ForeignKey('users.id' , ondelete="CASCADE"), primary_key=True)
    events_id = db.Column(db.Integer, db.ForeignKey('events.id', ondelete="CASCADE"), primary_key=True)
    attending = db.Column(db.Enum('undefined', 'yes', 'maybe', 'no', name='attending_states'), default="undefined")
    user = db.relationship('User', backref=db.backref('invitations', cascade='delete'))