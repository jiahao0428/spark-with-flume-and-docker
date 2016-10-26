import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.flume._
import org.mongodb.scala._
import org.mongodb.scala.model.Filters._
import org.mongodb.scala.model.Updates._
import org.mongodb.scala.model.UpdateOptions
import org.mongodb.scala.bson.collection.mutable.Document
import org.bson


val ssc = new StreamingContext(sc, Seconds(2))
val flumeStream = FlumeUtils.createStream(ssc, "namenode", 4545, StorageLevel.MEMORY_ONLY_2)
val lines = flumeStream.map {
            	e => new String(e.event.getBody().array(), "UTF-8")
            }
val words = lines.flatMap(_.split(" "))
val wordCounts = words.map(x => (x, 1)).reduceByKey(_ + _)

wordCounts.print()

wordCounts.foreachRDD ( rdd => {
	rdd.foreachPartition( partitionOfRecords => {
		val mongoClient: MongoClient = MongoClient("mongodb://mongo")
                val database: MongoDatabase = mongoClient.getDatabase("meetup")
                val collection: MongoCollection[Document] = database.getCollection("rsvps")
		
		partitionOfRecords.foreach( y => {
			//val documents = Document("keyword" -> y._1, "count" -> y._2)

			val updateObservable = collection.updateOne(equal("keyword", y._1), combine(inc("count", y._2), setOnInsert("keyword", y._1)), new UpdateOptions().upsert(true)).subscribe((updateResult: result.UpdateResult) => println(updateResult))
			
		})
		
		mongoClient.close()	
	})
})


ssc.start()
ssc.awaitTermination()
