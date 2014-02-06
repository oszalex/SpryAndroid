from google.appengine.ext import ndb
import endpoints
from protorpc import messages
from protorpc import message_types
from protorpc import remote

import datetime


class CategoryModel(ndb.Model):
	name = ndb.StringProperty(required=True)
	parent = ndb.KeyProperty(kind='CategoryModel', default=None)



#
# seed root category
#

# look for all 
root_query = CategoryModel.query(CategoryModel.name=="all").fetch(1)

#list is empty
if not root_query:
	#no root create it
	root_category_key=CategoryModel(name="all", parent=None).put()
else:
	#found get id
	root_category_key=root_query[0].key


'''
protorpc Messages
'''

class User(messages.Message):
	id = messages.StringField(1)
	name = messages.StringField(2, required=True)
	events = messages.StringField(3, repeated=True)


class Category(messages.Message):
	id = messages.StringField(1)
	name = messages.StringField(2,required=True)
	parent = messages.StringField(3, default=root_category_key.urlsafe())

class Event(messages.Message):
	name = messages.StringField(1, required=True)
	creator = messages.StringField(2, required=True)
	datetime = message_types.DateTimeField(3, required=True)
	place = messages.StringField(4, required=True)
	participants = messages.StringField(5, repeated=True)
	category = messages.StringField(6, default=root_category_key.urlsafe())



class UserList(messages.Message):
	items = messages.MessageField(User, 1, repeated=True)

class CategoryList(messages.Message):
	items = messages.MessageField(Category, 1, repeated=True)

class EventList(messages.Message):
	items = messages.MessageField(Event, 1, repeated=True)




'''
ndb Models
'''

class EventModel(ndb.Model):
	name = ndb.StringProperty(required=True)
	creator = ndb.KeyProperty(kind='User', required=True)
	datetime = ndb.DateTimeProperty(auto_now_add=True, required=True)
	place = ndb.GeoPtProperty()
	participants = ndb.KeyProperty(kind='User', repeated=True)
	category = ndb.KeyProperty(CategoryModel, default=root_category_key.urlsafe())

class UserModel(ndb.Model):
	name = ndb.StringProperty(required=True)
	events = ndb.KeyProperty(EventModel, repeated=True)
	



CATUPDATE_RESOURCE_CONTAINER = endpoints.ResourceContainer(
        Category,
        key=messages.StringField(2, required=True))

EV_UPDATE_RESOURCE_CONTAINER = endpoints.ResourceContainer(
        Event,
        key=messages.StringField(2, required=True))




@endpoints.api(name='bro', version='v4')

class BroApi(remote.Service):

	#
	# CATEGORIES
	#
	@endpoints.method(message_types.VoidMessage, CategoryList,
		name='category.list',
		path='categories',
		http_method='GET')
	def list_categories(self, unused_request):
		categories = []
		for category in CategoryModel.query():
			if category.parent is None:
				categories.append(Category(id=category.key.urlsafe(), name=category.name, parent="None"))
			else:
				categories.append(Category(id=category.key.urlsafe(), name=category.name, parent=category.parent.urlsafe()))

		return CategoryList(items=categories)


	@endpoints.method(Category, Category,
		name='category.insert',
		path='category',
		http_method='POST')
	def insert_categories(self, request):
		cat_query = CategoryModel.query(CategoryModel.name==request.name, CategoryModel.parent==ndb.Key(urlsafe=request.parent)).fetch(1)

		#list is empty
		if not cat_query:
			#no root create it
			cm=CategoryModel(name=request.name, parent=ndb.Key(urlsafe=request.parent))
			cm.put()
		else:
			#found
			cm=cat_query[0]
		return Category(id=cm.key.urlsafe(), name=request.name, parent=cm.parent.urlsafe())


	@endpoints.method(CATUPDATE_RESOURCE_CONTAINER, Category,
		path='category/{key}',
		http_method='PUT',
		name='category.update')
	def update_categories(self, request):
		cat = ndb.Key(urlsafe=request.key).get()

		if cat is None:
			message = 'No entity with the id "%s" exists.' % request.id
			raise endpoints.NotFoundException(message)

		else:
			cat.name = request.name
			cat.put()

		return Category(id=cat.key.urlsafe(), name=request.name, parent=cat.parent.urlsafe())




	#
	# EVENTS
	#
	@endpoints.method(message_types.VoidMessage, EventList,
		name='event.list',
		path='events',
		http_method='GET')
	def list_categories(self, unused_request):
		events = []
		for event in EventModel.query():
			events.append(Event(
				name=event.name, 
				creator=event.creator, 
				place=event.place, 
				datetime=event.datetime, 
				participants=event.participants, 
				category=event.category
				))

		return EventList(items=events)


	@endpoints.method(Event, Event,
		name='event.insert',
		path='events',
		http_method='POST')
	def insert_categories(self, request):
		event_query = EventModel.query(
			EventModel.name==request.name, 
			EventModel.creator==request.creator,
			EventModel.place==request.place,
			EventModel.datetime==request.datetime,
			EventModel.participants==request.participants,
			EventModel.category==request.category
			).fetch(1)

		#list is empty
		if not event_query:
			#no root create it

			lat, _ , lon = request.place.partition(" ")

			ev=EventModel(
				name=request.name, 
				creator=ndb.Key(urlsafe=request.creator), 
				place=datastore_types.GeoPt(lat=lat, lon=lon), 
				datetime=utils.DatetimeValueFromString(request.datetime),
				participants=[ndb.Key(urlsafe=p_key) for p_key in request.parent],
				category=ndb.Key(urlsafe=request.category))
			ev.put()
		else:
			#found
			ev=event_query[0]

		return Event(id=ev.key.urlsafe(), name=request.name, parent=ev.parent.urlsafe())


	@endpoints.method(EV_UPDATE_RESOURCE_CONTAINER, Event,
		path='event/{key}',
		http_method='PUT',
		name='event.update')
	def update_categories(self, request):
		ev = ndb.Key(urlsafe=request.key).get()

		if ev is None:
			message = 'No entity with the id "%s" exists.' % request.id
			raise endpoints.NotFoundException(message)

		else:
			#TODO update more attributes!
			ev.name = request.name
			ev.put()

		return Event(id=ev.key.urlsafe(), name=request.name, parent=ev.parent.urlsafe())





	#
	# USER
	#
	@endpoints.method(message_types.VoidMessage, UserList,
		name='user.list',
		path='users',
		http_method='GET')
	def list_categories(self, unused_request):
		users = []
		for user in UserModel.query():
			users.append(User(name=user.name,events=user.events))

		return UserList(items=users)


	@endpoints.method(User, User, name='user.insert', path='users', http_method='POST')
	def insert_categories(self, request):
		user_query = UserModel.query(UserModel.name==request.name).fetch(1)

		#list is empty
		if not user_query:
			#no root create it

			usr=UserModel(name=request.name)
			usr.put()
		else:
			#found
			usr=user_query[0]

		return User(id=usr.key.urlsafe(), name=request.name)


	@endpoints.method(EV_UPDATE_RESOURCE_CONTAINER, Event,
		path='event/{key}',
		http_method='PUT',
		name='event.update')
	def update_categories(self, request):
		ev = ndb.Key(urlsafe=request.key).get()

		if ev is None:
			message = 'No entity with the id "%s" exists.' % request.id
			raise endpoints.NotFoundException(message)

		else:
			#TODO update more attributes!
			ev.name = request.name
			ev.put()

		return Event(id=ev.key.urlsafe(), name=request.name, parent=ev.parent.urlsafe())





application = endpoints.api_server([BroApi])