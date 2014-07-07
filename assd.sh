#!/bin/bash
##########################################################
# Advanced Software Systems Development Module
#
# Applications Setup / Installed:
#
# Eclipse (with ADT) and Android SDK Automated Installer
#
# by Alex Kretzschmar (c)2014
# for UEA Linux
##########################################################

###
# Want to include some variables here
# 
# var1 = link to adtbundle
# var2 = link to moduledocs.zip
# var3 = path /opt/UEA/ASSD/
###

echo "Setting up Linux for Advanced Software Systems Development"
echo "Updating package repositories..."
sudo apt-get update -yqq

###########################################################
#
# Setup directories and permissions for ASSD module
#
###########################################################
 
echo "Setting up directories..."

sudo mkdir -p /opt/UEA/ASSD/
sudo mkdir -p /home/uea_user/Desktop/ASSD/
sudo chown -R nobody:users /opt/UEA/ASSD
sudo chown -R uea_user:users /home/uea_user/Desktop/ASSD
sudo chmod -R 777 /opt/UEA/ASSD 
sudo chmod 777 -R /home/uea_user/Desktop/ASSD

# Download module documentation (to be completed later)
echo "Downloading module documentation (labsheets, source code, etc)..."

# cd /home/uea_user/Desktop/ASSD/
# mkdir moduledocs directory
# wget moduledocs.zip

###########################################################
#
# Eclipse download and installation
#
###########################################################

echo "Installing pre-requisite packages for Eclipse..."
sudo apt-get install -yqq curl expect default-jdk

echo "Downloading Eclipse (ADT Bundle)..."
cd /opt/UEA/ASSD
wget http://dl.google.com/android/adt/adt-bundle-linux-x86_64-20140624.zip -O /opt/UEA/ASSD/adt-bundle.zip
unzip /opt/UEA/ASSD/adt-bundle.zip
rm adt-bundle.zip
ln -s /opt/UEA/ASSD/adt-bundle-linux-x86_64-20140624/eclipse/eclipse /home/uea_user/Desktop/ASSD/eclipse

###########################################################
#
# Android SDK download and installation
# 
# Uses expect to auto accept license agreement prompts
# 
###########################################################
 

echo "Downloading Android SDK components..."
expect -c '
set timeout -1   ;
spawn sudo /opt/UEA/ASSD/adt-bundle-linux-x86_64-20140624/sdk/tools/android update sdk -u; 
expect { 
    "Do you accept the license" { exp_send "y\r" ; exp_continue }
    eof
}
'

###########################################################
#
# Completion and tidy up
#
###########################################################

echo "Complete."
echo "Please use the new ASSD folder on your Desktop to launch Eclipse."
