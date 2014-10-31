Event App
===

Dear C0der, 

welcome to our Codebase! We have the following folders:

> `BackEndTest` small android app from alex to test backend 
> `ressources` logos, etc.  
> `MeetMeAndroid` latest application but does not work on older devices


API
===

API Proposal. ">" means that is outgoing (request), "<" is incoming (response).

Login
---

    > POST <host>/user/login
    > BasicAuth: <base64>
    > ...

    < 200 OK
    < json: { userid: <id>, ... }

Events
---

    > GET <host>/user/<id>/events
    > BasicAuth: <base64>
    > ...

    < 200 OK
    < json: [{id: '', name: 'party hard', ... }, ...]


There are lots of more to add here ... or somewhere else (maybe in the wiki?)
