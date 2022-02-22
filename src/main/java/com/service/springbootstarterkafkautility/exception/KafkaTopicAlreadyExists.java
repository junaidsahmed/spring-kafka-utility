package com.service.springbootstarterkafkautility.exception;

/**
 * @author Junaid Shakeel
 * @project Spring Kafka Starter Utility
 */
public class KafkaTopicAlreadyExists extends Exception{

   public KafkaTopicAlreadyExists(String message){
        super(message);
    }
}
