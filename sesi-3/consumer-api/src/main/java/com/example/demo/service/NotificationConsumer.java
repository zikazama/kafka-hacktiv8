package com.example.demo.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.kafka.support.KafkaHeaders;

@Service
public class NotificationConsumer {

    @KafkaListener(
        topics = "notifications", 
        groupId = "notification-group",
        containerFactory = "kafkaListenerContainerFactory" // Refer ke bean yang benar
    )
    public void handleNotification(
        @Payload String message,
        @Header(KafkaHeaders.RECEIVED_KEY) String orderId
    ) {
        System.out.println("[NOTIF] Order " + orderId + ": " + message);
    }
}