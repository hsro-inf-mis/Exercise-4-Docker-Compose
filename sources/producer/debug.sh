#!/usr/bin/env bash

PAYARA_VERSION="4.1.2.173"
PAYARA_JAR=payara-micro.jar
export RABBITMQ_HOST="localhost"

if [ ! -e "$PAYARA_JAR" ]; then
    curl -o ${PAYARA_JAR} $"https://s3-eu-west-1.amazonaws.com/payara.fish/Payara+Downloads/Payara+$PAYARA_VERSION/payara-micro-$PAYARA_VERSION.jar"
fi

gradle build
WAR_FILE=$(find $"`pwd`/build/libs/" -name "*.war" | tac | tail -n 1)
java -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005 -jar ${PAYARA_JAR} --deploy ${WAR_FILE}