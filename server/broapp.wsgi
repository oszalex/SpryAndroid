#!/usr/bin/python
import sys
import logging
from os.path import abspath, dirname

logging.basicConfig(stream=sys.stderr)
sys.path.insert(0, abspath(dirname(__file__)))

from broapp import app as application
