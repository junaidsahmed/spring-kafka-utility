# spring-kafka-starter-utility

I have written a custom Kafka starter using spring in order to minimize and avoid the same producer-consumer configurations across the microservices

all you need to do just add a maven dependency in your spring project and add the properties like bootstrap server and topic name then without doing any configuration you can produce and consume messages

		<dependency>
			<groupId>com.service</groupId>
			<artifactId>spring-boot-starter-kafka-utility</artifactId>
			<version>1.0.0</version>
		</dependency>

You can set different properties in application.yml or application.properties
    e.g
            kafka:
                bootStrapServer: "localhost:9092"
                producer:
                    producerBootStrapServer: "localhost:9092"
                consumer:
                    consumerBootstrapServer: "localhost:9092"
                    groupId: "mytopic_group"
                    autoOffsetReset: "earliest"
                    maxPollSize: 80
                    enableAutoCommit: true


This starter will provide following features
**Single topic creation
Multiple topic creation
sending messages on any specific kafka topic
consume messages from kafka**

1) for creating topic on kafka
`   @Autowired
   KafkaTopicInterface kafkaTopicInterface;
   public boolean createTopic() {
        String topicname="mytopic"; int noOfpartition=3; short replicationFactor=1;
       return kafkaTopicInterface.createTopic(topicname,noOfpartition,replicationFactor);
   }`

2) for sending message on kafka topic
`    
  @Autowired
   KafkaProducerConfigProperties kafkaProducerConfigProperties;

   public String produceJsonObjects() {
   String topicname="mytopic";
   kafkaProducerConfigProperties.kafkaTemplateForJsonObjects().send(topicname,new MyData(1,"my topic value"));
   }
`
3) for Consume message from kafka
   
`   @KafkaListener(topics = "mytopic",clientIdPrefix = "json",containerFactory = "kafkaListenerJSONObjectContainerFactory")
   public void getMessagesFromKafka(MyData data)  {
       System.out.println(data.toString());
   }`