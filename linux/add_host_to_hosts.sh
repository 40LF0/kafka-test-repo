#!/bin/bash

# Purpose: This script adds a new host name and its corresponding IP address to the /etc/hosts file.
# It requires root privileges to modify system files.

# Check if the correct number of arguments (2) is provided.
# If not, display the correct usage and exit with an error.
if [ "$#" -ne 2 ]; then
    echo "Usage: $0 [Host Name] [IP Address]"
    exit 1
fi

# Assign the first argument as the host name and the second as the IP address.
HOST_NAME=$1
IP_ADDRESS=$2

# Check if the provided host name already exists in the /etc/hosts file.
# If it does, print an error message and exit with an error.
if grep -q "$HOST_NAME" /etc/hosts; then
    echo "Error: Host name $HOST_NAME already exists in /etc/hosts."
    exit 1
fi

# If the host name does not exist, append the new host name and IP address to the /etc/hosts file.
# The 'tee' command is used with 'sudo' to gain the necessary permissions for file modification.
echo "$IP_ADDRESS $HOST_NAME" | sudo tee -a /etc/hosts

# Print a confirmation message indicating successful addition.
echo "Host $HOST_NAME with IP $IP_ADDRESS added to /etc/hosts."