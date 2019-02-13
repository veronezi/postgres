#!/bin/bash
set -e

mvn --settings /tmp/.travis.settings.xml install -Dverbose=true -DTMP=$HOME/volume
