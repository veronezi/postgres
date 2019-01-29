#!/bin/bash
set -e

IFS=', ' read -r -a array <<< "$WAIT_FOR"
for element in "${array[@]}"
do
    /usr/local/bin/dockerize -wait tcp://${element/@/:} -wait-retry-interval 5s -timeout 240s
done

echo "[postgres] All dependencies are online. Starting up this service now. [$(whoami)]"

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
