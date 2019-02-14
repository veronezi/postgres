#!/bin/bash
set -e

echo "restoring $RESTORE_BASE_BACKUP with user $(whoami)"
rm -Rf /var/lib/postgresql/data/*
mc cp $MINIOSRV_NAME/$UPLOAD_BASEBACKUP_PATH/$RESTORE_BASE_BACKUP /var/lib/postgresql/data/base.tar
cd /var/lib/postgresql/data/ && tar xf base.tar && rm base.tar
cp /opt/restore.conf /var/lib/postgresql/data/recovery.conf
chown -R postgres:postgres /var/lib/postgresql/data