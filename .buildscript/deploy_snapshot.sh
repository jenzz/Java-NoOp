#!/bin/bash

JDK_VERSION="oraclejdk8"
BRANCH="develop"

if [ "$TRAVIS_JDK_VERSION" != "$JDK_VERSION" ]; then
    echo "Skipping snapshot deployment: Only deploying with '$JDK_VERSION'."
elif [ "$TRAVIS_BRANCH" != "$BRANCH" ]; then
  echo "Skipping snapshot deployment: Only deploying from '$BRANCH'."
elif [ "$TRAVIS_PULL_REQUEST" == "true" ]; then
  echo "Skipping snapshot deployment: Not deploying pull requests."
else
    mvn clean deploy --settings=".buildscript/settings.xml"
fi