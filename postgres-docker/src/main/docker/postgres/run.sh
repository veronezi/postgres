#!/bin/bash
set -e

if [[ -z "$RESTORE_BASE_BACKUP" ]]
then
    echo "[postgres] ...regular startup."
    mkdir -p /opt/backup/wal
    mkdir -p /opt/backup/base
else
    echo "[postgres] ...restore startup."
    source /opt/restore_base_backup.sh
fi

chown -R postgres:postgres /opt/backup
/usr/local/bin/docker-entrypoint.sh "$@"
