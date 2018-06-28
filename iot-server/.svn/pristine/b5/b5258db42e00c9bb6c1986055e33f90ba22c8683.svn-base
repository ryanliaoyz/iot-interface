#!/bin/sh

kill -9 `cat tpid`

nohup java -jar iot-1.0.0.jar --spring.config.location=runtime.properties  > iot.log 2>&1 &
echo $! > tpid

echo Start Success!