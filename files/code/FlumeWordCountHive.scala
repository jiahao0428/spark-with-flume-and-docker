import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.flume._
import org.apache.spark.sql.types._


val ssc = new StreamingContext(sc, Seconds(2))
val flumeStream = FlumeUtils.createStream(ssc, "namenode", 4545, StorageLevel.MEMORY_ONLY_2)
val lines = flumeStream.map {
                e => new String(e.event.getBody().array(), "UTF-8")
            }
val words = lines.flatMap(_.split(" "))
val wordCounts = words.map(x => (x, 1)).reduceByKey(_ + _)

wordCounts.print()

wordCounts.foreachRDD ( rdd => {

	val tempDF = rdd.toDF("key", "value")
        tempDF.registerTempTable("temp")

        val data = sqlContext.sql("select key, value FROM temp")
        data.write.mode("append").saveAsTable("src")

})


ssc.start()
ssc.awaitTermination()
