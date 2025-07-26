package com.hcl.producer.config;

import com.hcl.producer.constants.KafkaTopic;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic getAdmin() {
        return TopicBuilder.name(KafkaTopic.PRODUCE_MESSAGE_TOPIC)
            .build();
    }
}
