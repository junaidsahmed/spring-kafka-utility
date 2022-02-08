package com.service.springbootstarterkafkautility.implemetation;


import com.service.springbootstarterkafkautility.config.KafkaTopicConfigProperties;
import com.service.springbootstarterkafkautility.interfaces.KafkaTopicInterface;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.ListTopicsOptions;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.KafkaException;
import org.springframework.kafka.config.TopicBuilder;


import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@Slf4j
public class KafkaTopicImp implements KafkaTopicInterface {

    private KafkaTopicConfigProperties kafkaTopicConfigProperties;

   public KafkaTopicImp(KafkaTopicConfigProperties kafkaTopicConfigProperties){
        this.kafkaTopicConfigProperties=kafkaTopicConfigProperties;
    }


    @Override
    public boolean createTopic(String topicName, int noOfPartitions, short noOfReplicas) {
        try{
         this.publishTopic( TopicBuilder.name(topicName)
                 .partitions(noOfPartitions)
                 .replicas(noOfReplicas)
                 .build());
         return true;
        }
        catch (KafkaException exception){
            log.error("exception occurred -->>  "+ exception);
        }
        return false;
    }

    /* This method publish topic on kafka using admin client */
    @Override
    public void publishTopic(NewTopic topic) {
        kafkaTopicConfigProperties.getKafkaAdmin().listTopics();
        kafkaTopicConfigProperties.getKafkaAdmin().createTopics(Collections.singleton(topic));
    }

    @Override
    public void publishMultipleTopic(List<NewTopic> topicsList) {
        kafkaTopicConfigProperties.getKafkaAdmin().createTopics(topicsList);
    }

    @Override
    public Set<String> getTopicList() throws ExecutionException, InterruptedException {
        ListTopicsOptions listTopicsOptions = new ListTopicsOptions();
        listTopicsOptions.listInternal(true);
         return kafkaTopicConfigProperties.getKafkaAdmin().listTopics(listTopicsOptions).names().get();
    }

}
