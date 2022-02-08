package com.service.springbootstarterkafkautility.interfaces;

import org.apache.kafka.clients.admin.NewTopic;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;


public interface KafkaTopicInterface {

     boolean  createTopic(String topicName,int noOfpartitions,short noOfreplicas) ;
     void publishTopic(NewTopic topic);
     void publishMultipleTopic(List<NewTopic> lst);
     Set<String> getTopicList() throws ExecutionException, InterruptedException;
}