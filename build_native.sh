#!/bin/sh
mvn clean install -Dnative -Dquarkus.native.container-build=true -Dquarkus.container-image.build=true
