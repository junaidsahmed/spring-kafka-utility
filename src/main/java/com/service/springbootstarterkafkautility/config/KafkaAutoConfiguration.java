package com.service.springbootstarterkafkautility.config;


import com.service.springbootstarterkafkautility.implemetation.KafkaTopicImp;
import com.service.springbootstarterkafkautility.interfaces.KafkaTopicInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Junaid Shakeel
 * @project Spring Kafka Starter Utility
 * @email junaid.shakeel@live.com
 */
@Configuration
@ConditionalOnClass(KafkaTopicInterface.class)
@EnableConfigurationProperties({KafkaTopicConfigProperties.class,KafkaProducerConfigProperties.class,KafkaConsumerConfig.class})
public class KafkaAutoConfiguration {

     KafkaTopicConfigProperties kafkaTopicConfigProperties;

     KafkaProducerConfigProperties kafkaProducerConfigProperties;

     KafkaConsumerConfig kafkaConsumerConfig;

    @Autowired
    public KafkaAutoConfiguration(KafkaTopicConfigProperties kafkaTopicConfigProperties, KafkaProducerConfigProperties kafkaProducerConfigProperties,KafkaConsumerConfig kafkaConsumerConfig) {
        this.kafkaTopicConfigProperties = kafkaTopicConfigProperties;
        this.kafkaProducerConfigProperties = kafkaProducerConfigProperties;
        this.kafkaConsumerConfig= kafkaConsumerConfig;
    }

    @Bean
    @ConditionalOnMissingBean
    public KafkaTopicInterface kafkaService(){
        return new KafkaTopicImp(kafkaTopicConfigProperties);
    }
}
