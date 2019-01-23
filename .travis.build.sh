#!/bin/bash
set -e

mvn --settings /tmp/.travis.settings.xml install
