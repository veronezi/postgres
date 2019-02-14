#!/bin/bash
set -e

IFS=', ' read -r -a array <<< "$WAIT_FOR"
for element in "${array[@]}"
do
    /usr/local/bin/dockerize -wait tcp://${element/@/:} -wait-retry-interval 5s -timeout 240s
done

echo "All dependencies are online. Starting up this service now."

mc config host add $MINIOSRV_NAME http://$MINIO_URL $MINIO_ACCESS_KEY $MINIO_SECRET_KEY
# running the same command for the postgres user
su - postgres -c "mc config host add $MINIOSRV_NAME http://$MINIO_URL $MINIO_ACCESS_KEY $MINIO_SECRET_KEY"

mc --quiet mb $MINIOSRV_NAME/$UPLOAD_BASEBACKUP_PATH/ -p
mc --quiet mb $MINIOSRV_NAME/$UPLOAD_WAL_PATH/ -p

if [[ -z "$RESTORE_BASE_BACKUP" ]]
then
    echo "[postgres] ...regular startup."
else
    echo "[postgres] ...restore startup."
    source /opt/restore_base_backup.sh
fi
/usr/local/bin/docker-entrypoint.sh "$@"
