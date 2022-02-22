package com.service.springbootstarterkafkautility.interfaces;

import com.service.springbootstarterkafkautility.exception.KafkaTopicAlreadyExists;
import org.apache.kafka.clients.admin.NewTopic;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * @author Junaid Shakeel
 * @project Spring Kafka Starter Utility
 * @email junaid.shakeel@live.com
 */


public interface KafkaTopicInterface {

     boolean  createTopic(String topicName,int noOfpartitions,short noOfreplicas) throws KafkaTopicAlreadyExists;
     void adviceTopic(NewTopic topic);
     void adviceMultipleTopic(List<NewTopic> lst);
     Set<String> getTopicList() throws ExecutionException, InterruptedException;
     boolean createMultipleTopic(List<String> topicList,int noOfpartitions,short noOfreplicas) throws ExecutionException, InterruptedException;
}