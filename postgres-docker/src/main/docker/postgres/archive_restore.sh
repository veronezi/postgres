#!/bin/bash
set -e

FULL_PATH=$1
FILE_NAME=$2

echo "[postgres] restoring $(pwd)/$FULL_PATH with user $(whoami)"
mc cp $MINIOSRV_NAME/$UPLOAD_WAL_PATH/$FILE_NAME $(pwd)/$FULL_PATH
echo "[postgres] restore complete"
