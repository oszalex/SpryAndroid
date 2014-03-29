# port 591

from broapp import app
from OpenSSL import SSL

ctx = SSL.Context(SSL.SSLv23_METHOD)
ctx.use_privatekey_file('cert/ssl.key')
ctx.use_certificate_file('cert/ssl.cert')

app.run(host='0.0.0.0',port=8080,
	debug = True, ssl_context=ctx)