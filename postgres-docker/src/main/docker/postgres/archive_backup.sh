#!/bin/bash
set -e

FULL_PATH=$1
FILE_NAME=$2

echo "archiving $(pwd)/$FULL_PATH with user $(whoami)"
cp $(pwd)/$FULL_PATH /opt/backup/wal/$FILE_NAME