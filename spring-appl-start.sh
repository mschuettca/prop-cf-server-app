#!/bin/sh
java -Djava.security.egd=file:/dev/./urandom -jar /app.jar --spring.config.location=mac-application.properties
