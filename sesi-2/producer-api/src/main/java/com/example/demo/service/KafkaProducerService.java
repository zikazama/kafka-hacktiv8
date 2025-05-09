package com.example.demo.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message, String key) {
        String topic = "my_topic";  // Specify the topic name here
        kafkaTemplate.send(topic, key, message);  // Send the message with key
    }
}
