#!/bin/bash -u
##########################################################
# Database Manipulate Module
#
# Applications Setup / Installed:
#
# postgresql-9.3, pgadmin3, dia
#
# by Alex Kretzschmar (c)2014
# for UEA Linux
##########################################################

echo "Setting up UEA Linux for Database Manipulation\n\n"
echo "Updating package repositories..."
sudo apt-get update -yqq

###########################################################
#
# Setup directories and permissions for ASSD module
#
###########################################################

echo "Setting up directories..."

sudo mkdir -p "/home/uea_user/Desktop/UEA_Linux/Databases"
sudo chown -R uea_user:users "/home/uea_user/Desktop/UEA_Linux/Databases"
sudo chmod 777 -R "/home/uea_user/Desktop/UEA_Linux/Databases"

# Download module documentation (to be completed later)
echo "Downloading module documentation (labsheets, source code, etc)..."

# cd /home/uea_user/Desktop/UEA_Linux/Databases/
# mkdir moduledocs directory
# wget moduledocs.zip

###########################################################
#
# Downloading and configuring postgresql software
#
###########################################################

echo "Installing postgresql..."
sudo apt-get install -yqq postgresql pgadmin3 dia


###########################################################
#
# Completion and tidy up
#
###########################################################

echo "Congratulations! Database module setup complete."
