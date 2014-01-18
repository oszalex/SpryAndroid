#import models

from protorpc import remote
from google.appengine.ext import ndb
import endpoints
from endpoints_proto_datastore.ndb import EndpointsModel


class User(EndpointsModel):
	name = ndb.StringProperty()
	password = ndb.StringProperty()
#    friends = ndb.KeyProperty(kind='User', repeated=True)

class Event(EndpointsModel):
	name = ndb.StringProperty()
	datetime = ndb.DateTimeProperty(auto_now_add=True)
	place = ndb.GeoPtProperty()
    # TODO: own model for categories
	category = ndb.StringProperty(choices=('all', 'drinking'))

class Category(EndpointsModel):
	name = ndb.StringProperty()


@endpoints.api(name='broapi', version='v3', description='Bro Api')
class BroApi(remote.Service):

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

	@User.query_method(path='user/{id}', name='user.get')
	def UserGet(self, query):
		return query

	##
	# delete user
	##

	@User.query_method(path='user/{id}',http_method='DELETE', name='user.delete')
	def UserDelete(self, query):
		query.delete()
		return "OK"



#    @User.method(path='user/me', name='user.getauth')
#    def GetAuthe(self, query):
#        return query


application = endpoints.api_server([BroApi], restricted=False)