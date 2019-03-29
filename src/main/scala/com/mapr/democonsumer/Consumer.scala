package com.mapr.democonsumer

import org.apache.spark.sql.SparkSession


object Consumer {
  def main(args: Array[String]): Unit ={

//    @transient lazy val logger = Logger.getLogger(getClass.getName)
//    Logger.getLogger("org").setLevel(Level.OFF)

    val stream = args(0)
    val topic  = args(1)

    val srcTopic = stream + ":" + topic
    val spark = SparkSession.builder()
      .appName("Order-Consumer")
      .master("local[*]")
      .getOrCreate()

    val brokers = "localhost:9092"

    import spark.implicits._

    val topicDF = spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers",brokers)
      .option("startingOffsets","earliest")
      .option("failOnDataLoss",false)
      .option("subscribe",srcTopic)
      .option("maxOffsetsPerTrigger",100)
      .load()

    val resultDF = topicDF.select($"topic".cast("String"), $"partition", $"offset",
      $"key".cast("String"),$"value".cast("String"),
      $"timestamp".cast("String") as "ts")

    val query = resultDF.writeStream.outputMode("append")
      .format("console")
      .option("truncate",false)
      .option("numRows",100).start()

    query.awaitTermination()

  }

}
