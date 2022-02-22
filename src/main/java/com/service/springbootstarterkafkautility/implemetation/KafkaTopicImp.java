package com.service.springbootstarterkafkautility.implemetation;


import com.service.springbootstarterkafkautility.config.KafkaTopicConfigProperties;
import com.service.springbootstarterkafkautility.exception.KafkaTopicAlreadyExists;
import com.service.springbootstarterkafkautility.interfaces.KafkaTopicInterface;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.ListTopicsOptions;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.KafkaException;
import org.springframework.kafka.config.TopicBuilder;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * @author Junaid Shakeel
 * @project Spring Kafka Starter Utility
 * @email junaid.shakeel@live.com
 */

@Slf4j
public class KafkaTopicImp implements KafkaTopicInterface {

    private KafkaTopicConfigProperties kafkaTopicConfigProperties;

   public KafkaTopicImp(KafkaTopicConfigProperties kafkaTopicConfigProperties){
        this.kafkaTopicConfigProperties=kafkaTopicConfigProperties;
    }


    @Override
    public boolean createTopic(String topicName, int noOfPartitions, short noOfReplicas) throws KafkaTopicAlreadyExists {
        try{
            boolean alreadyExistsTopic= getTopicList().stream().anyMatch(existedTopic -> existedTopic.equals(topicName));
         if(alreadyExistsTopic)
             throw new KafkaTopicAlreadyExists(topicName+" topic already exists in kafka try with different name");
            this.adviceTopic( TopicBuilder.name(topicName)
                 .partitions(noOfPartitions)
                 .replicas(noOfReplicas)
                 .build());
         return true;
        }
        catch (KafkaException | ExecutionException | InterruptedException exception){
            log.error("exception occurred -->>  "+ exception);
        }
        return false;
    }

    /* function create topic on kafka using admin client */
    @Override
    public void adviceTopic(NewTopic topic) {
        kafkaTopicConfigProperties.getKafkaAdmin().listTopics();
        kafkaTopicConfigProperties.getKafkaAdmin().createTopics(Collections.singleton(topic));
    }

    /* This function create multiple topics on kafka using admin client */
    @Override
    public void adviceMultipleTopic(List<NewTopic> topicsList) {
        kafkaTopicConfigProperties.getKafkaAdmin().createTopics(topicsList);
    }

    @Override
    public Set<String> getTopicList() throws ExecutionException, InterruptedException {
        ListTopicsOptions listTopicsOptions = new ListTopicsOptions();
        listTopicsOptions.listInternal(true);
         return kafkaTopicConfigProperties.getKafkaAdmin().listTopics(listTopicsOptions).names().get();
    }

    @Override
    public boolean createMultipleTopic(List<String>topicList ,int noOfpartitions, short noOfreplicas) throws ExecutionException, InterruptedException {
        List<NewTopic> newTopics = new ArrayList<>();
        List<String> alreadyExistTopic= new ArrayList<>(getTopicList());
        for(String name: topicList){
            if(!alreadyExistTopic.contains(name))
                {   newTopics.add(TopicBuilder.name(name)
                        .partitions(noOfpartitions)
                        .replicas(noOfreplicas)
                        .build());
                 }
        }
        if(!newTopics.isEmpty()) {
            adviceMultipleTopic(newTopics);
            log.warn("these topics are already present on kafka but remaining topics is created "+
                    alreadyExistTopic.stream().filter(topicList::contains).collect(Collectors.toList()));
            return true;
        }
        log.warn("these topics are already present on kafka "+
                alreadyExistTopic.stream().filter(topicList::contains).collect(Collectors.toList()));
        return false;
    }

}
