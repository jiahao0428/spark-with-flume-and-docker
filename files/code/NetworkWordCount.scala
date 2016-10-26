import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.{Seconds, StreamingContext}


val sparkConf = new SparkConf().setAppName("NetworkWordCount").setMaster("yarn-client")
val ssc = new StreamingContext(sc, Seconds(2))


val lines = ssc.socketTextStream("namenode", 8001, StorageLevel.MEMORY_AND_DISK_SER)
val words = lines.flatMap(_.split(" "))
val wordCounts = words.map(x => (x, 1)).reduceByKey(_ + _)
wordCounts.print()
ssc.start()
ssc.awaitTermination()
