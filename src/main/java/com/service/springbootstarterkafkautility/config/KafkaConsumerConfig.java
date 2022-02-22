package com.service.springbootstarterkafkautility.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.converter.JsonMessageConverter;


import java.util.HashMap;
import java.util.Map;

/**
 * @author Junaid Shakeel
 * @project Spring Kafka Starter Utility
 * @email junaid.shakeel@live.com
 */

@Slf4j
@ConfigurationProperties("kafka.consumer")
public class KafkaConsumerConfig{
    private String consumerBootstrapServer;

    private String groupId;

    private Boolean enableAutoCommit;

    private Integer maxPollSize;

    private String autoOffsetReset;

    public Boolean getEnableAutoCommit() {
        return enableAutoCommit;
    }

    public void setEnableAutoCommit(Boolean enableAutoCommit) {
        this.enableAutoCommit = enableAutoCommit;
    }

    public Integer getMaxPollSize() {
        return maxPollSize;
    }

    public void setMaxPollSize(Integer maxPollSize) {
        this.maxPollSize = maxPollSize;
    }

    public String getConsumerBootstrapServer() {
        return consumerBootstrapServer;
    }

    public void setConsumerBootstrapServer(String consumerBootstrapServer) {
        this.consumerBootstrapServer = consumerBootstrapServer;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getAutoOffsetReset() {
        return autoOffsetReset;
    }

    public void setAutoOffsetReset(String autoOffsetReset) {
        this.autoOffsetReset = autoOffsetReset;
    }

    /*
     * returns consumer config properties
     * */
    public Map<String,Object> consumerConfigs(){
        Map<String,Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, consumerBootstrapServer);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);

        if(this.autoOffsetReset != null)
            props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        if(this.maxPollSize!= null || this.maxPollSize !=0)
            props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPollSize);
        if(this.enableAutoCommit != null)
            props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);

        return props;
    }

    /* consumer factory for JSON Objects */

    public ConsumerFactory<String, Object> consumerFactoryForObjects() {
        /* not using the following approach because at the time of deserialization header information also required
        * and generic deserialization would not possible by following approach
        *  */
//        JsonDeserializer<Object> jsonDeserializer = new JsonDeserializer();
//        jsonDeserializer.setUseTypeMapperForKey(true);
//        jsonDeserializer.addTrustedPackages("*");
        //return new DefaultKafkaConsumerFactory<>(consumerConfigsForJsonObjects(),new StringDeserializer(),jsonDeserializer);
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }
    /* consumer factory for JSON Objects, and it is using message converter to map message into objects */
    @Bean
    public   ConcurrentKafkaListenerContainerFactory<? ,?> kafkaListenerJSONObjectContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String,Object> factory=
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryForObjects());
        // converts kafka message data in java objects
        factory.setMessageConverter(new JsonMessageConverter());
        return factory;
    }

    public ConsumerFactory<String, String> stringConsumerFactory() {
    return new DefaultKafkaConsumerFactory<>(
            consumerConfigs(), new StringDeserializer(), new StringDeserializer()
    );
}

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerStringContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(stringConsumerFactory());
        return factory;
    }
}
