#!/bin/bash
set -e

FULL_PATH=$1
FILE_NAME=$2

echo "[postgres] archiving $(pwd)/$FULL_PATH with user $(whoami)"
mc cp $(pwd)/$FULL_PATH $MINIOSRV_NAME/$UPLOAD_WAL_PATH/$FILE_NAME
echo "[postgres] archive complete"
