package solutions

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.StructType

object SensorStream {

  def main(args: Array[String]): Unit = {
    // set up able configuration
    val spark = SparkSession.builder.master("local").appName("SensorStream").getOrCreate()
    import spark.implicits._

	// schema for sensor data
    //TODO: left everything as string for testing purposes, must change brand_id and customer_no to "long"
    val userSchema = new StructType()
    .add("brand_id", "string")
    .add("addresses[].country_code", "string")
    .add("customer_no", "string")
    .add("first_name", "string")
    .add("last_name", "string")
    .add("addresses[].street1", "string")
    .add("addresses[].street2", "string")
    .add("addresses[].city", "string")    
    .add("addresses[].state", "string")
    .add("addresses[].zip_code", "string")
    .add("phones[].phone_number", "string")
    .add("emails[].email_address", "string")
    .add("alternate_keys[].key_code", "string")    
    .add("alternate_keys[].key", "string")
    .add("joined_at", "string")
    .add("joined_on", "string")

    //load df so we can create schema of stream
    val df = spark.read.option("sep", "\t").csv("/user/user01/adhoc_customer_20180824-1513-1k.txt")

    // schema for sensor data
    //val userSchema = df.schema

    // parse the lines of data into sensor objects
    val sensorCsvDF = spark.readStream.option("sep", "\t").schema(userSchema).csv("/user/user01/stream/")

    // filter sensor data for low psi
    val filterSensorDF = sensorCsvDF.toJSON

    // Start the computation
    println("start streaming")


    val query = filterSensorDF.writeStream.format("kafka").option("kafka.bootstrap.servers", "host1:9092").option("topic", "/user/mapr/streams/chicos/customer:adhoc").option("checkpointLocation", "/user/user01/checkpoint").start()

    // Wait for the computation to terminate
    query.awaitTermination()

  }

}
