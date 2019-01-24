#!/bin/bash
set -e

IFS=', ' read -r -a array <<< "$WAIT_FOR"
for element in "${array[@]}"
do
    /usr/local/bin/dockerize -wait tcp://${element/@/:} -wait-retry-interval 5s -timeout 240s
done

echo "All dependencies are online. Starting up this service now."

/usr/bin/java -agentlib:jdwp=transport=dt_socket,server=y,address=5005,suspend=$SUSPEND \
 -Ddb_host=$DB_HOST -Ddb_port=$DB_PORT \
 -Don_docker=true -jar /opt/ft.jar "$@"

echo "All good! The tests are green."

# wait forever
/usr/bin/tail -f /dev/null