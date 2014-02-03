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

class CategoryList(messages.Message):
	items = messages.MessageField(Category, 1, repeated=True)


#class EventModel(ndb.Model):
#	#_message_fields_schema = ("id", "name", "creator", "datetime", "place", "category")
#
#	creator = ndb.KeyProperty(kind='User', required=True)
#	name = ndb.StringProperty(required=True)
#	datetime = ndb.DateTimeProperty(auto_now_add=True, required=True)
#	place = ndb.GeoPtProperty()
#	#category = ndb.KeyProperty(Category, default=Category(name="all").key)
#	participants = ndb.KeyProperty(kind='User', repeated=True)

#class UserModel(ndb.Model):
#	#_message_fields_schema = ("id", "name", "password")
#
#	name = ndb.StringProperty(required=True)
#	password = ndb.StringProperty(required=True)
#	events = ndb.KeyProperty(Event, repeated=True)
	


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
