#!/bin/bash
set -e

IFS=', ' read -r -a array <<< "$WAIT_FOR"
for element in "${array[@]}"
do
    /usr/local/bin/dockerize -wait tcp://${element/@/:} -wait-retry-interval 5s -timeout 240s
done

echo "[postgres] All dependencies are online. Starting up this service now."

if [[ "$RESTORE_MODE" == "true" ]]
then
    source /opt/restore_base_backup.sh
fi

/usr/local/bin/docker-entrypoint.sh "$@"
