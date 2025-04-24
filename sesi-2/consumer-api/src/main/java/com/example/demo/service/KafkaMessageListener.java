package com.example.demo.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaMessageListener {

    // This will listen to the "my_topic" topic with the group "console-consumer-32603"
    @KafkaListener(topics = "my_topic", groupId = "console-consumer")
    public void listen(String message) {
        // This method will be called whenever a new message is consumed from the topic "my_topic"
        System.out.println("Received message: " + message);
    }
}
