#!/bin/bash
set -e

echo "[postgres] creating base backup with user $(whoami)..."
mv /opt/backup/base/base.tar /opt/backup/base/base.tar.old.$(date +%s) > /dev/null | true
pg_basebackup -U $POSTGRES_USER -Ft -X none -D /opt/backup/base
