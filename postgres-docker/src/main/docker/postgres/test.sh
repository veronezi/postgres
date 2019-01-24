#!/bin/bash
set -e

psql $POSTGRES_DB $POSTGRES_USER -c "CREATE TABLE names (name_id serial PRIMARY KEY, name CHAR (10) NOT NULL);"

for i in {1..10}
do
    psql $POSTGRES_DB $POSTGRES_USER -c "INSERT INTO names (name) VALUES ('$i');"
done

psql $POSTGRES_DB $POSTGRES_USER -c "SELECT pg_switch_wal();"
