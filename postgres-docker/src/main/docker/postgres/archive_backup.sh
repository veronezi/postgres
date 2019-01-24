#!/bin/bash
set -e

FULL_PATH=$1
FILE_NAME=$2

echo "archiving $(pwd)/$FULL_PATH"
curl -s -f -F upload=@$(pwd)/$FULL_PATH $BACKUP_URL/upload/$FILE_NAME