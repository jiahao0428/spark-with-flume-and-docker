#!/bin/bash

exec docker -H :4000 exec -it namenode spark-shell --master yarn-client --queue default --jars /code/jars/spark-streaming-flume_2.10-1.6.0.jar,/code/jars/flume-ng-sdk-1.6.0.jar,/code/jars/mongo-scala-driver_2.11-1.1.1-alldep.jar -i /code/FlumeWordCount.scala

