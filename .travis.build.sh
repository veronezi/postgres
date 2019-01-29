#!/bin/bash
set -e

mvn --settings /tmp/.travis.settings.xml install -Dverbose=true -DTMP=/tmp/my_volumes
