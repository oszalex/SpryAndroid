#import models

from protorpc import remote
from google.appengine.ext import ndb
import endpoints
from endpoints_proto_datastore.ndb import EndpointsModel
from endpoints_proto_datastore.ndb import EndpointsAliasProperty





class Event(EndpointsModel):

	_message_fields_schema = (
        "id",
        "name",
        "creator",
        "datetime",
        "place",
        "category",
        "participants"
    )

	participants = ndb.KeyProperty(kind='User', repeated=True)
	creator = ndb.KeyProperty(kind='User')
	name = ndb.StringProperty()
	datetime = ndb.DateTimeProperty(auto_now_add=True)
	place = ndb.GeoPtProperty()
    # TODO: own model for categories
	category = ndb.StringProperty(choices=('all', 'drinking'))




'''
User Model
'''
class User(EndpointsModel):

	_message_fields_schema = (
        "id",
        "name",
        "password",
        "events"
    )

	name = ndb.StringProperty()
	
	'''
	password
	'''
	md5password = ndb.StringProperty()

	def PasswordSet(self, value):
		if not isinstance(value, basestring):
			raise TypeError("Password must be a string.")
	
		self.md5password = value + "md5"
		self.put()

	@EndpointsAliasProperty(setter=PasswordSet, required=True)
	def password(self):
		return self.md5password


	'''
	events
	'''
	events = ndb.KeyProperty(Event)

	#def create_event(self, e_name, e_datetime, e_place, e_category):
	#	# requires two writes
	#	event = Event(name=e_name, creator = self.key, datetime=e_datetime, place=e_place, category=e_category)
	#	event.put()
	#	self.events.append(event)
	#	self.put()
	#
	#def get_events(self):
	#	return ndb.get_multi(self.events)






class Category(EndpointsModel):

	_message_fields_schema = (
        "id",
        "name"
    )

	name = ndb.StringProperty()



@endpoints.api(name='broapi', version='v3', description='Bro Api')
class BroApi(remote.Service):

	##
	# insert new user
	##

	@User.method(path='user', http_method='POST', name='user.insert')
	def UserInsert(self, user):

		user.put()
		return user

	##
	# Get users
	##

	@User.query_method(path='users', name='user.list')
	def UserlList(self, query):
		return query

	##
	# Get user
	##

	@User.method(path='user/{id}', http_method="GET", name='user.get', request_fields=("id",))
	def UserGet(self, user):

		return user

	##
	# delete user
	##

	@User.method(path='user/{id}',http_method='DELETE', name='user.delete', request_fields=("id",), response_fields=(),)
	def UserDelete(self, user_object):
		
		if not user_object.from_datastore:
			raise endpoints.NotFoundException("User not found.")

		user_object.key.delete()

		return user_object

	##
	# update user
	##

	@User.method(path="user/{id}", http_method="PUT", name="user.update")
	def UserUpdate(self, user):
		#if not card.from_datastore or card.user != endpoints.get_current_user():
			#    raise endpoints.NotFoundException("Card not found.")

		user.put()

		return user



#    @User.method(path='user/me', name='user.getauth')
#    def GetAuthe(self, query):
#        return query


application = endpoints.api_server([BroApi], restricted=False)