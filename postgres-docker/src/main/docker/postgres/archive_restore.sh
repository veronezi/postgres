#!/bin/bash
set -e

FULL_PATH=$1
FILE_NAME=$2

cp /opt/backup/wal/$FILE_NAME $(pwd)/$FULL_PATH