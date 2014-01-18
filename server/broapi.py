#import models

from protorpc import remote
from google.appengine.ext import ndb
import endpoints
from endpoints_proto_datastore.ndb import EndpointsModel


class User(EndpointsModel):

    # Basic info.
    name = ndb.StringProperty()
#    birth_day = ndb.DateProperty()
#    friends = ndb.KeyProperty(kind='User', repeated=True)

class Event(EndpointsModel):
    when = ndb.DateTimeProperty()
    where = ndb.GeoPtProperty()
    # TODO: own model for categories
    what = ndb.StringProperty(choices=('all', 'drinking'))


@endpoints.api(name='broapi', version='v3', description='Bro Api')
class BroApi(remote.Service):

	@User.method(path='user', http_method='POST', name='user.insert')
	def UserInsert(self, user):

		user.put()
		return user

	@User.query_method(path='users', name='user.list')
	def UserlList(self, query):
		return query

#	@User.method(path='user/{id}',http_method='DELETE', name='user.delete')
#	def UserDelete(self, query):
#		return query


application = endpoints.api_server([BroApi], restricted=False)