package com.service.springbootstarterkafkautility.config;


import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.kafka.annotation.KafkaBootstrapConfiguration;
import org.springframework.kafka.annotation.KafkaListenerConfigurer;


import java.util.Properties;

@ConfigurationProperties(value = "kafka")
@Slf4j
public class KafkaTopicConfigProperties {

     String bootStrapServer;

    /* Create Admin instance with custom properties */
    public Admin getKafkaAdmin() {
        Properties configs = new Properties();
      //  log.info("bootstrap server " + this.bootStrapServer);
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServer);
        return Admin.create(configs) ;
    }

    public String getBootStrapServer() {
        return bootStrapServer;
    }

    public void setBootStrapServer(String bootStrapServer) {
        this.bootStrapServer = bootStrapServer;
    }
}
