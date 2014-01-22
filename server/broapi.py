from protorpc import remote
from google.appengine.ext import ndb
import endpoints
from endpoints_proto_datastore.ndb import EndpointsModel
from endpoints_proto_datastore.ndb import EndpointsAliasProperty
from google.appengine.ext import db
from google.appengine.api import users
import datetime


class Category(EndpointsModel):
	_message_fields_schema = ("id", "name", "parent")
	name = ndb.StringProperty(required=True)
	parent = ndb.KeyProperty(kind='Category')


class Event(EndpointsModel):

	_message_fields_schema = ("id", "name", "creator", "datetime", "place", "category")

	creator = ndb.KeyProperty(kind='User', required=True)
	name = ndb.StringProperty(required=True)
	datetime = ndb.DateTimeProperty(auto_now_add=True, required=True)
	place = ndb.GeoPtProperty()
	category = ndb.KeyProperty(Category, default=Category(name="all").key)
	participants = ndb.KeyProperty(kind='User', repeated=True)

class User(EndpointsModel):

	_message_fields_schema = ("id", "name", "password")

	name = ndb.StringProperty(required=True)
	md5password = ndb.StringProperty(required=True)
	events = ndb.KeyProperty(Event, repeated=True)
	
	def PasswordSet(self, value):
		if not isinstance(value, basestring):
			raise TypeError("Password must be a string.")
	
		self.md5password = value + "md5"
		self.put()

	@EndpointsAliasProperty(setter=PasswordSet, required=True)
	def password(self):
		return self.md5password

	def create_event(self, e_name, e_datetime, e_place, e_category):
		self.put()
		
		event = Event(name=e_name, creator = self.key, datetime=e_datetime, place=e_place, category=e_category)
		event.participants.append(self.key)
		event.put()
		self.events.append(event.key)
		self.put()
	
	def get_events(self):
		return ndb.get_multi(self.events)


#### Test

#c = Category(name="all")
#c.put()

#c2 = Category(name="something", parent=c.key)
#c2.put()

#alice = User(name="Alice", md5password="sdadas", events=[])
#alice.put()

#e = Event(creator=alice.key, name="instameeting")
#e.put()





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
		#find user
		qry = User.query(ancestor=user.key)

		user_object = qry.get()

		if user_object is None:
			raise endpoints.NotFoundException("User not found.")

		return user_object

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

		#find user
		qry = User.query(ancestor=user.key)

		user_object = qry.get()

		if user_object is None:
			raise endpoints.NotFoundException("User not found.")

		#update user
		user_object.name = user.name
		user_object.events = user.events
		user_object.password = user.password

		#store changes
		user_object.put()

		return user_object


application = endpoints.api_server([BroApi], restricted=False)