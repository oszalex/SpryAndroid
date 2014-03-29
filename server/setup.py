import os
from setuptools import setup

# Utility function to read the README file.
# Used for the long_description.  It's nice, because now 1) we have a top level
# README file and 2) it's easier to type in the README file than to put a raw
# string in below ...
def read(fname):
    return open("README.md").read()

setup(
    name = "broapp",
    version = "0.0.1",
    author = "Christian Hotz-Behofsits",
    author_email = "chris.hotz.behofsits@gmail.com",
    description = ("The reference broapp server"),
    license = "commercial",
    keywords = "reference bro broapp",
    url = "https://github.com/inkrement/bro/tree/dev/server",
    packages=['broapp', 'tests'],
    long_description=read('README'),
    classifiers=[
        "Development Status :: 1 - Alpha",
        "Topic :: Server",
        "License :: Commercial",
    ],
    install_requires=[
	    'setuptools',
	    'Flask',
	    'Flask-HTTPAuth',
	    'Flask-SQLAlchemy',
	    'Flask-Script',
		'Jinja2',
		'MarkupSafe',
		'SQLAlchemy',
		'Werkzeug',
		'cffi',
		'cryptography',
		'itsdangerous',
		'marshmallow',
		'passlib',
		'pyOpenSSL',
		'pycparser',
		'six',
		'wsgiref',
	],
)