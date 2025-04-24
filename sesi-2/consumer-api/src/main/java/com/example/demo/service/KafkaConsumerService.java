package com.example.demo.service;

import org.springframework.kafka.support.KafkaHeaders;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "my_topic", groupId = "my_group")
    public void consumeMessage(String message, @Header(KafkaHeaders.RECEIVED_KEY) String key) {
        System.out.println("Received message: " + message);
        System.out.println("Message key: " + key);
    }
}
