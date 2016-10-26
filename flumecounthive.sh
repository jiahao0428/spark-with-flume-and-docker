#!/bin/bash

exec docker -H :4000 exec -it namenode spark-shell --jars /code/jars/spark-streaming-flume_2.10-1.6.0.jar,/code/jars/flume-ng-sdk-1.6.0.jar,/usr/local/hive/lib/mysql-connector-java.jar -i /code/FlumeWordCountHive.scala

