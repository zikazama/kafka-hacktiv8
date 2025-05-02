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

    // Method baru untuk study case 3
    public void sendNotification(String orderId, String message) {
        String topic = "notifications";
        // Key = orderId, Value = message (expire setelah 30 menit)
        kafkaTemplate.send(topic, orderId, message);
    }

    public void updateDeliveryStatus(String orderId, String status) {
        String topic = "delivery_status";
        // Key = orderId, Value = status (hanya retain last update)
        kafkaTemplate.send(topic, orderId, status);
    }
}
