

#Create stream

maprcli stream create -path /user/som/streams/retailstream -produceperm p -consumeperm p -topicperm p -ttl 900

#Verify the stream was created sucessfully with this command:

maprcli stream info -path /user/som/streams/retailstream



#Create Topic

maprcli stream topic create -path /user/som/streams/retailstream -topic orders

#Verify the topic was created successfully with this command:

maprcli stream topic list -path /user/som/streams/retailstream




#Invoke Producer jar

java -cp `mapr classpath`:/tmp/mapr-streams-example-1.0-SNAPSHOT-jar-with-dependencies.jar com.mapr.demo.samplestreams.Run producer /user/som/sqoop_import/retail_db/orders /user/som/streams/retailstream:orders

java -cp `mapr classpath`:/tmp/mapr-streams-example-1.0-SNAPSHOT-jar-with-dependencies.jar com.mapr.democonsumer.Consumer  /user/som/streams/retailstream orders


#mapr user

java -cp `mapr classpath`:/user/mapr/tmp/mapr-streams-example-1.0-SNAPSHOT-jar-with-dependencies.jar com.mapr.demo.samplestreams.Run producer /user/mapr/retail_db/orders /user/mapr/streams/retailstream:orders

java -cp `mapr classpath`:/user/mapr/tmp/mapr-streams-example-1.0-SNAPSHOT-jar-with-dependencies.jar com.mapr.democonsumer.Consumer /user/som/streams/retailstream orders



#stream check point




#Delete streams

maprcli stream delete -path /user/som/streams/retailstream






maprcli stream create -path /user/som/streams/retailstream -produceperm p -consumeperm p -topicperm p -ttl 900

#Verify the stream was created sucessfully with this command:

maprcli stream info -path /user/som/streams/retailstream

maprcli stream topic create -path /user/som/streams/retailstream -topic orders

#Verify the topic was created successfully with this command:

maprcli stream topic list -path /user/som/streams/retailstream




#Invoke Producer jar

java -cp `mapr classpath`:/tmp/mapr-streams-example-1.0-SNAPSHOT-jar-with-dependencies.jar com.mapr.demo.samplestreams.Run producer /user/som/sqoop_import/retail_db/orders /user/som/streams/retailstream:orders