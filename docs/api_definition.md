#API definition (Backend)

> Standard-Format: JSON  
> Version: 3  
> URL (local) :http://localhost:8080/_ah/api/broapi/v3/  
> URL (remote):https://broappapi.appspot.com/_ah/api/broapi/v3/users  

## Entity Models
Following entities are available: user, event and category.

>user[
>>id: unique identifer  
>>name: username  
>>friends: list of user-objects  
>>password: pw
>
>]


>event[
>>id: unique identifer  
>>name: name of specific event  
>>place: koordinates of 'target'  (std: current user location)  
>>datetime: specific date and time in future (std:current datetime)  
>>category: id of specific category (std: all)
>]

>category[
>>id: unique identifer  
>>name: name of specific category  
>
>]


##RESTful API

###User

###list all users

> URL : http://localhost:8080/_ah/api/broapi/v3/users  
> TYPE: HTTP-GET  
> RESPONSE: JSON Array containing all users

###get specific user
> URL : http://localhost:8080/_ah/api/broapi/v3/user/{id}  
> TYPE: HTTP-GET  
> RESPONSE: JSON Object containing specific user  

If user not found a 501 ErrorCode will be returned.

###get authenticated user
> URL : http://localhost:8080/_ah/api/broapi/v3/user/me  
> TYPE: HTTP-GET  
> RESPONSE: JSON Object containing the authenticated user  


###create user

> URL : http://127.0.0.1:8080/_ah/api/broapi/v3/user  
> TYPE: HTTP-POST  
> HEADER: Content-Type  
> RESPONSE: Copy of the Request

To create a user you have to create a HTTP-POST Request. This Rq hast to be a JSON Object with the specific attributes (name, friends, pw).

>{  
>  "name" : {USERNAME},  
>  "friends" : [ {CommaSeparatedListOfUserIDs} ],  
>  "password" : {HashedPassword}  
>}


###delete user

> URL : http://127.0.0.1:8080/_ah/api/broapi/v3/user/{id}  
> TYPE: HTTP-DELETE  
> RESPONSE: TODO

