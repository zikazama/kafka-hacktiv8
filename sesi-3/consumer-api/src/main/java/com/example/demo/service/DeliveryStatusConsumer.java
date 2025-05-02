package com.example.demo.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class DeliveryStatusConsumer {
    @KafkaListener(topics = "delivery_status", groupId = "delivery-group")
    public void handleStatusUpdate(
        @Payload String status,
        @Header(KafkaHeaders.RECEIVED_KEY) String orderId
    ) {
        System.out.println("[STATUS] Order " + orderId + ": " + status);
        // Update database dengan status terakhir
    }
}