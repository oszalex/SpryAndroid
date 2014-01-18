 #API definition

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


###create User

> URL : http://127.0.0.1:8080/_ah/api/broapi/v3/user  
> TYPE: HTTP-POST  
> HEADER: Content-Type

To create a user you have to create a HTTP-POST Request. This Rq hast to be a JSON Object with the specific attributes (name, friends, pw).

>{  
>  "name" : {USERNAME},  
>  "friends" : [ {CommaSeparatedListOfUserIDs} ],  
>  "password" : {HashedPassword}  
>}

 
