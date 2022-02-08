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


@Slf4j
@ConfigurationProperties("kafka.producer")
public class KafkaProducerConfigProperties {

    String bootStrapServer;

    public String getBootStrapServer() {
        return bootStrapServer;
    }

    public void setBootStrapServer(String bootStrapServer) {
        this.bootStrapServer = bootStrapServer;
    }

    /*
            returns kafka producer's factory for Custom Json Objects
        */
    public <T> ProducerFactory<String,T> producerFactoryForJsonObjects(){
        Map<String,Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootStrapServer);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
        log.info("boot strap server "+bootStrapServer);
        return new DefaultKafkaProducerFactory<>(props);
    }
    /*
         returns kafka producer's factory for Strings
     */
    public ProducerFactory<String,String> producerFactoryForString(){
        Map<String,Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootStrapServer);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        log.info("boot strap server "+bootStrapServer);
        return new DefaultKafkaProducerFactory<>(props);
    }

    public <T> KafkaTemplate<String,T> kafkaTemplateForJsonObjects(){
        return new KafkaTemplate<>(producerFactoryForJsonObjects());
    }
    public KafkaTemplate<String,String> kafkaTemplateForString(){

        return new KafkaTemplate<>(producerFactoryForString());
    }
}