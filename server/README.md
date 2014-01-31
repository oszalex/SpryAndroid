#Server API

Der Server soll eine Restful API bereitstellen. Über HTTP können JSON Objecte abgefragt werden. Für Authentifizierung wird OAuth2 verwendet, sodass Facebook, Twitter etc. als Auth-Provider verwendet werden können. Als Plattform wird google app engine verwendet, wobei sogenannte Endpoints verwendet werden um schnell und einfach APIs erstellen zu können. NDB wird als Datenbank und Python als Programmiersprache vorgeschlagen.

API Definitionen: http://docs.bro.apiary.io/


##Links

 * OAuth 2 Slides - http://www.slideshare.net/aaronpk/an-introduction-to-oauth-2
 * Outh2 and RESTful - http://www.slideshare.net/jcleblanc/securing-restful-apis-using-oauth-2-and-openid-connect
 * OAuth Google Apps engine - https://code.google.com/p/gae-simpleauth/




##TODO

 - Authentification
 - proper model definition
 - define api for user/friends
 - Seeding of Database
 - solve KeyProperty problems
 - delete direction
