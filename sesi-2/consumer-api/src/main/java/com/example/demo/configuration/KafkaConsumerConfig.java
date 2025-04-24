package com.example.demo.configuration;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    private static final String BOOTSTRAP_SERVERS = "localhost:9093";
    private static final String GROUP_ID = "my-consumer-group";
    private static final String TOPIC = "my_topic";

    private Map<String, Object> consumerProps() {
        Map<String, Object> consumerProps = new HashMap<>();
        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);  // Kafka bootstrap server
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);  // Consumer group id
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);  // Deserializer for key
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);  // Deserializer for value
        return consumerProps;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        Map<String, Object> consumerProps = consumerProps();  // Reusing the consumer properties
        ConsumerFactory<String, String> consumerFactory = new DefaultKafkaConsumerFactory<>(consumerProps);

        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setConcurrency(1);  // Adjust concurrency (number of threads) if needed
        factory.getContainerProperties().setPollTimeout(3000);  // Optional: Set poll timeout for consumers
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE); // Enable manual offset commit

        return factory;
    }

    @Bean
    public MessageListenerContainer messageListenerContainer() {
        Map<String, Object> consumerProps = consumerProps();  // Reusing the consumer properties
        DefaultKafkaConsumerFactory<String, String> consumerFactory = new DefaultKafkaConsumerFactory<>(consumerProps);

        ContainerProperties containerProps = new ContainerProperties(TOPIC);
        containerProps.setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);  // Use manual offset commit

        ConcurrentMessageListenerContainer<String, String> container =
            new ConcurrentMessageListenerContainer<>(consumerFactory, containerProps);

        // Set up a message listener if needed
        container.setupMessageListener(new MessageListener<String, String>() {
            @Override
            public void onMessage(ConsumerRecord<String, String> record) {
                System.out.println("Consumed message: " + record.value() + " with key: " + record.key());
                // Manually acknowledge the message here after processing
            }
        });

        return container;
    }
}
