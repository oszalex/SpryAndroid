from google.appengine.ext import ndb
import endpoints
from protorpc import messages
from protorpc import message_types
from protorpc import remote

import datetime





class CategoryModel(ndb.Model):
	#_message_fields_schema = ("id", "name", "parent")
	name = ndb.StringProperty(required=True)
	parent = ndb.KeyProperty(kind='CategoryModel', default=None)


#key_a = ndb.Key(CategoryModel, "");
#person = Person(key=key_a)
#person.put()

#new_key_a =ndb.Key(Person, email);
#a = new_key_a.get()
#if a is not None:
#    return

root_category=CategoryModel(name="all", parent=None)
root_category_key=root_category.put()

#root_category_key=ndb.Key(CategoryModel, "all")
#root_category = CategoryModel(key=root_category_key)
#root_category.put()

#print root_category_key

#CategoryModel.get_or_insert(root_category_key)


class Category(messages.Message):
	name = messages.StringField(1,required=True)
	parent = messages.StringField(2, default=root_category_key.urlsafe())

class Event(messages.Message):
	name = messages.StringField(1, required=True)
	creator = messages.StringField(2, required=True)
	datetime = message_types.DateTimeField(3, required=True)
	place = messages.StringField(4, required=True)
	participants = messages.StringField(5, required=True)
	category = messages.StringField(6, default=root_category_key.urlsafe())

class User(messages.Message):
	name = messages.StringField(1, required=True)
	events = messages.StringField(2, repeated=True)



class CategoryList(messages.Message):
	items = messages.MessageField(Category, 1, repeated=True)

class EventList(messages.Message):
	items = messages.MessageField(Event, 1, repeated=True)

class UserList(messages.Message):
	items = messages.MessageField(User, 1, repeated=True)




class EventModel(ndb.Model):
	name = ndb.StringProperty(required=True)
	creator = ndb.KeyProperty(kind='User', required=True)
	datetime = ndb.DateTimeProperty(auto_now_add=True, required=True)
	place = ndb.GeoPtProperty()
	participants = ndb.KeyProperty(kind='User', repeated=True)
	category = ndb.KeyProperty(Category, default=Category(name="all").key)

class UserModel(ndb.Model):
	name = ndb.StringProperty(required=True)
	events = ndb.KeyProperty(Event, repeated=True)
	



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
				categories.append(Category(name=category.name, parent="None"))
			else:
				categories.append(Category(name=category.name, parent=category.parent.urlsafe()))

		return CategoryList(items=categories)


	@endpoints.method(Category, Category,
		name='category.insert',
		path='category',
		http_method='POST')
	def insert_categories(self, request):
		cm=CategoryModel(name=request.name, parent=ndb.Key(urlsafe=request.parent)).put()
		return Category(name=request.name, parent=cm.get().parent.urlsafe())



application = endpoints.api_server([BroApi])
