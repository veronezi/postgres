#!/bin/bash
set -e

mkdir /opt/volume/data -p
/usr/bin/docker-entrypoint.sh "$@"
