#!/usr/bin/env python
# -*- coding: utf-8 -*-

from flask.ext.testing import TestCase
from .. import app, db

import unittest

class BaseTestCase(TestCase):
    def create_app(self):
        return app

    def setUp(self):
        db.create_all()

    def tearDown(self):
        db.session.remove()
        db.drop_all()

    def test_some_json(self):
        response = self.client.get("/info/")
        self.assertEquals(response.data, "Hello Bro!")


if __name__ == '__main__':
    unittest.main()