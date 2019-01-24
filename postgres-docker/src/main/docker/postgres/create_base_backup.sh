#!/bin/bash
set -e

echo "[postgres] creating base backup..."
echo "[postgres] ... creating the backup file..."
pg_basebackup -U $POSTGRES_USER -Ft -X none -D /opt/basebackup
echo "[postgres] ... uploading the backup file..."
curl -s -f -F upload=@/opt/basebackup/base.tar $BACKUP_URL/basebackup