package banquemisr.challenge05.email.service.configuration;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Value("${kafka.bootstrap.servers}")
    private String kafkaBootstrapServers;

    @Value("${kafka.template.default-topic}")
    private String kafkaTemplateDefaultTopic;

    @Value("${kafka.consumer.group-id}")
    private String kafkaConsumerGroupId;

    @Value("${kafka.consumer.key-deserializer}")
    private String kafkaConsumerKeyDeserializer;

    @Value("${kafka.consumer.value-deserializer}")
    private String kafkaConsumerValueDeserializer;

    @Value("${kafka.consumer.properties.spring.json.type.mapping}")
    private String kafkaConsumerPropertiesSpringJsonTypeMapping;

    @Value("${kafka.consumer.enable-auto-commit}")
    private String kafkaConsumerEnableAutoCommit;

    @Value("${kafka.consumer.auto-offset-reset}")
    private String kafkaConsumerAutoOffsetReset;

    @Value("${kafka.listener.ack-mode}")
    private String kafkaListenerAckMode;

    @Value("${kafka.consumer.session.timeout.ms}")
    private String kafkaConsumerSessionTimeout;

    @Value("${kafka.consumer.max.poll.interval.ms}")
    private String kafkaConsumerMaxPollInterval;

    @Value("${kafka.consumer.heartbeat.interval.ms}")
    private String kafkaConsumerHeartbeatInterval;

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        factory.setConcurrency(3); // Set the concurrency level here
        return factory;
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapServers);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaConsumerGroupId);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, kafkaConsumerKeyDeserializer);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, kafkaConsumerValueDeserializer);
        config.put("spring.json.type.mapping", kafkaConsumerPropertiesSpringJsonTypeMapping);
        config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, kafkaConsumerEnableAutoCommit);
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaConsumerAutoOffsetReset);
        config.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, kafkaConsumerSessionTimeout);
        config.put("spring.kafka.listener.ack-mode", kafkaListenerAckMode);
        config.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, kafkaConsumerMaxPollInterval);
        config.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, kafkaConsumerHeartbeatInterval);

        return new DefaultKafkaConsumerFactory<>(config);
    }
}
