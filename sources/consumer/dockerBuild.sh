#!/usr/bin/env bash
gradle build assembleDist
tar -xf build/distributions/consumer-1.0-SNAPSHOT.tar -C build/distributions/
docker build -t inf-docker.fh-rosenheim.de/kupe184/dockerswarm/mis-week03-consumer:latest .