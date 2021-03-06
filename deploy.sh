#!/bin/bash

# To publish a build, use the Makefile
# make publish
# which will call out to this script
set -e

# Use maven-help-plugin to get the current project.version
VERSION=`mvn org.apache.maven.plugins:maven-help-plugin:2.1.1:evaluate -Dexpression=project.version | grep -v '\['`

echo "Publishing $VERSION"

echo "Build and deploy to sonatype (do not include the agent jar)"
mvn -s settings.xml -Dmaven.test.skip=true -P deploy deploy  -pl .,common,launcher

