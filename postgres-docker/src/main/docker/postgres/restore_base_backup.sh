#!/bin/bash
set -e

echo "[postgres] preparing restore mode..."
rm -Rf /var/lib/postgresql/data/*
curl -s -f $BACKUP_URL/basebackup/base.tar --output /var/lib/postgresql/data/base.tar
cd /var/lib/postgresql/data/ && tar xvf base.tar && rm base.tar
cp /opt/restore.conf /var/lib/postgresql/data/recovery.conf