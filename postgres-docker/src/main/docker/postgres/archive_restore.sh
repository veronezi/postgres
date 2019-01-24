#!/bin/bash
set -e

FULL_PATH=$1
FILE_NAME=$2

curl -s -f $BACKUP_URL/uploaded/$FILE_NAME --output $(pwd)/$FULL_PATH