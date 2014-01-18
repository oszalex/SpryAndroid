#!/bin/bash
# 
# Build a virtual environment suitable for running appengine. 
# This uses virtualenvwrapper to make the virtual environment.
# Which you can activate with 'workon appengine'
#
# Everyone loves one-liners!
# Mac one-liner:
#   $ curl -s https://raw.github.com/gist/1012769 | bash
# 
# Ubuntu one-liner:
#   $ sudo apt-get install curl && curl -s https://raw.github.com/gist/1012769 | bash
#
#
# Install steps:
# 1) Checks for virtualenvwrapper and install if needed.
# 2) Activates virtualenvwrapper and creates $WORKON_HOME
# 3) Makes 'appengine' virtual environment
# 4) Downloads and extracts google appengine source
# 5) pip installs a few extra packages
# 
# Ubuntu Install Help
# 1) First Install python2.5, python-setuptools
# 2) sudo add-apt-repository ppa:fkrull/deadsnakes
# 3) sudo apt-get update
# 4) sudo apt-get install curl python-setuptools python2.5
#
# CHANGELOG
# 10-22-12 - Fixing the url to check for appengine updates.
# 05-16-12 - Fixing typos in if statements.
# 11-01-11 - Adding support for Macosx Lion.
# 11-01-11 - Fixing bug in platform check which was always true.
# 10-03-11 - Adding support for Ubunutu.
 
PLATFORM=`uname -s`
VERSION=`uname -r`
 
if [[ $PLATFORM == "Linux" ]]
then
    # Check if python2.5 installed and install other prereqs
    # Ubuntu no longer has python2.5 in a main repository.
    which python2.5
    if [[ ! $? == 0 ]]
    then
        sudo add-apt-repository ppa:fkrull/deadsnakes
        sudo apt-get update
        sudo apt-get install curl python-setuptools python2.5-dev libssl-dev libbluetooth-dev
    fi
    # check for individual packages in case they already had python2.5
    # just in case
    if [[ ! -e /usr/bin/curl ]]
    then
        sudo apt-get install curl
    fi
    if [[ ! -e /usr/bin/easy_install ]]
    then
        sudo apt-get install python-setuptools
    fi
    # check for ssl
    if [[ ! -e /usr/include/openssl/ssl.h ]]
    then
        sudo apt-get install libssl-dev
    fi
    # check for libbluetooth-dev
    if [[ ! -e /usr/include/bluetooth/bluetooth.h ]]
    then
        sudo apt-get install libbluetooth-dev
    fi
fi
 
LATEST_VERSION=`curl -s https://appengine.google.com/api/updatecheck | awk '$1 ~/release/ {print $2};' | sed s/\"//g`
 
# Modify me as new versions are released.
APPENGINE_VERSION=google_appengine_$LATEST_VERSION.zip
 
 
which virtualenvwrapper.sh
VWNOTFOUND=$?
 
if [[ $VWNOTFOUND == 1 ]]
then
	echo 'Installing virtualenvwrapper...'
        sudo easy_install virtualenvwrapper
fi
 
if [[ ! $WORKON_HOME ]]
then
        export WORKON_HOME=$HOME/envs
fi
 
# make sure we are not dealing with a customize path
unset PYTHONPATH
 
mkdir -p $WORKON_HOME
source `which virtualenvwrapper.sh`
 
cd $WORKON_HOME
 
if [[ -e appengine ]] 
then
     echo "Deleting existing appengine ENV"
     rm -rf appengine
fi
 
mkvirtualenv --no-site-packages --distribute --python=python2.5 appengine
 
if [[ ! $VIRTUAL_ENV ]]
then
     echo "Problem running mkvirtualenv!"
     exit 1
fi
 
# Don't download the file over again.
if [[ ! -e $APPENGINE_VERSION ]]
then
    curl -O http://googleappengine.googlecode.com/files/$APPENGINE_VERSION
fi
 
echo "Extracting Google App Engine source..."
unzip -q $APPENGINE_VERSION -d $VIRTUAL_ENV
 
if [[ ! $? == 0 ]]
then
    echo "Problem unzipping file $APPENGINE_VERISON"
    echo "Please delete it and try again!"
    exit $?
fi
# Create a pth file for App Engine source
echo "$VIRTUAL_ENV/google_appengine" > $VIRTUAL_ENV/lib/python2.5/site-packages/gae.pth
 
# Link scripts
cd $VIRTUAL_ENV/bin
ln -s $VIRTUAL_ENV/google_appengine/*.py .
 
 
# The Real python2.5 prefix
REALPREFIX=`python2.5 -c "import sys;print sys.real_prefix"`
 
# link os.pyc to make appengine happy
# On lion pyc files are out of date :(
if [[ $PLATFORM == "Darwin" && $VERSION == "11.2.0" ]]
then
    echo "Fixing pyc files in $REALPREFIX/lib/python2.5/"
    sudo $REALPREFIX/bin/python2.5 -m compileall
fi
 
cd $VIRTUAL_ENV/lib/python2.5
echo "Linking os.pyc to $REALPREFIX/lib/python2.5/os.pyc"
rm -rf os.pyc
ln -s $REALPREFIX/lib/python2.5/os.pyc
 
# On mac's add some compiler flags.
if [[ $PLATFORM == "Darwin" ]]
then
     ARCHFLAGS="-arch i386 -arch x86_64"
fi
 
for package in webtest PIL fabric mock unittest2 coverage ipython==0.10.2
do
    echo "Installing $package ..."
    pip install -q $package
done
 
# SSL needs to be installed with sudo!! lame!
echo "Installing ssl with sudo:"
sudo $VIRTUAL_ENV/bin/pip install -q ssl
echo "Fixing perms"
sudo chown -R `whoami` $VIRTUAL_ENV
 
# uninstall webob as it conflicts with the bundled one in app engine.
echo "Uninstalling webob ..."
pip uninstall -q -y webob
 
echo "You are ready to roll. Edit .bashrc as needed and run 'workon appengine'"
 
if [[ $VWNOTFOUND == 1 ]]
then
        echo "Virtualenvwrapper has been installed."
        echo "Please add the following to your .bashrc file:"
        echo ' export WORKON_HOME=$HOME/envs'
        echo ' source `which virtualenvwrapper.sh`'
fi
