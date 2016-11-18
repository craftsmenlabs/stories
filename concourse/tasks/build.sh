#!/usr/bin/env bash

set -e -x

pushd stories
    mvn clean install
popd
