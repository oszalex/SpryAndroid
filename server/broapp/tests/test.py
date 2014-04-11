#!/usr/bin/env python
# -*- coding: utf-8 -*-

from flask.ext.testing import TestCase
from ..models import User
from .. import app, db

import unittest
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


if __name__ == '__main__':
    unittest.main()