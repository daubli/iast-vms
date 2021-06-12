#!/bin/bash

exec java -Djava.security.egd=file:/dev/./urandom ${JVM_ARGS} -jar /app.jar
