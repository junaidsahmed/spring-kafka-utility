package com.service.springbootstarterkafkautility.config;


import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Junaid Shakeel
 * @project Spring Kafka Starter Utility
 * @email junaid.shakeel@live.com
 */

@Slf4j
@ConfigurationProperties("kafka.producer")
public class KafkaProducerConfigProperties {

    String producerBootStrapServer;

    public String getProducerBootStrapServer() {
        return producerBootStrapServer;
    }

    public void setProducerBootStrapServer(String producerBootStrapServer) {
        this.producerBootStrapServer = producerBootStrapServer;
    }

    /*
            returns kafka producer's factory for Custom Json Objects
        */
    public  ProducerFactory<String,Object> producerFactoryForJsonObjects(){
        Map<String,Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, producerBootStrapServer);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
        log.info("boot strap server "+ producerBootStrapServer);
        return new DefaultKafkaProducerFactory<>(props);
    }
    /*
         returns kafka producer's factory for Strings
     */
    public ProducerFactory<String,String> producerFactoryForString(){
        Map<String,Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, producerBootStrapServer);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        log.info("boot strap server "+ producerBootStrapServer);
        return new DefaultKafkaProducerFactory<>(props);
    }

    public  KafkaTemplate<String,Object> kafkaTemplateForJsonObjects(){
        return new KafkaTemplate<>(producerFactoryForJsonObjects());
    }
    public KafkaTemplate<String,String> kafkaTemplateForString(){

        return new KafkaTemplate<>(producerFactoryForString());
    }
}