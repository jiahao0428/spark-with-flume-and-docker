#!/bin/bash

exec docker -H :4000 exec -it namenode spark-shell --jars /code/jiahao/jars/spark-streaming-flume_2.10-1.6.0.jar,/code/jiahao/jars/flume-ng-sdk-1.6.0.jar,/code/jiahao/jars/mongo-scala-driver_2.11-1.1.1-alldep.jar -i /code/jiahao/FlumeWordCount.scala
