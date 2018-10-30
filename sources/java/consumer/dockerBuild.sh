#!/usr/bin/env bash
gradle build assembleDist
tar -xf build/distributions/consumer-1.0-SNAPSHOT.tar -C build/distributions/
docker build -t mis-consumer:latest .