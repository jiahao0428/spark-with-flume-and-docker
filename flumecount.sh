#!/bin/bash

while getopts "i:" opt; do
        case $opt in
                i)
                        container_id=$OPTARG
                        ;;
        esac
done


exec docker -H :4000 exec -it $container_id spark-shell --jars /code/jiahao/jars/spark-streaming-flume_2.10-1.6.0.jar,/code/jiahao/jars/flume-ng-sdk-1.6.0.jar,/code/jiahao/jars/mongo-scala-driver_2.11-1.1.1-alldep.jar -i /code/jiahao/FlumeWordCount.scala
