#!/usr/bin/env python
# -*- coding: utf-8 -*-


'''
             _   ____  _____   ____   
            | | |  _ \|  __ \ / __ \  
   __ _  ___| |_| |_) | |__) | |  | | 
  / _` |/ _ \ __|  _ <|  _  /| |  | | 
 | (_| |  __/ |_| |_) | | \ \| |__| | 
  \__, |\___|\__|____/|_|  \_\\____(_)
   __/ |                              
  |___/                                


'''


from flask.ext.testing import TestCase
from ..models import User
from .. import app, db


import base64

class BaseTestCase(TestCase):
    def create_app(self):
        return app

    def setUp(self):
        db.create_all()

        user = User("chris", "some@mail.com", "male", "123")
        db.session.add(user)
        db.session.commit()

    def tearDown(self):
        db.session.remove()
        db.drop_all()

    def getSecured(self, route):
        response = self.client.get(route, 
            headers={'Authorization': 'Basic Y2hyaXM6MTIz'} )
        return response


'''
    def test_some_json(self):
        response = self.client.get("/info/")
        self.assertEquals(response.data, "Hello Bro!")

    def test_find_user(self):
        user = User.query.filter_by(username="chris").first()

        assert user is not None

    def test_login(self):
        response = self.client.get("/users/me", 
        	headers={'Authorization': 'Basic Y2hyaXM6MTIz'} )
        self.assert200(response)

    def test_login_required(self):
        response = self.client.get("/users/me")
        self.assert401(response)
'''
