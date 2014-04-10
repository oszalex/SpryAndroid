'''
info

'''

from flask import Blueprint, jsonify
import subprocess

info = Blueprint('info', __name__)

@info.route("/")
def hello():
	return "Hello Bro!"


@info.route("/version")
def version():
    return jsonify({"version": subprocess.check_output(["git", "describe", "--tag"])})

