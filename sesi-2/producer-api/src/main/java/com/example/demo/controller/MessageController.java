package com.example.demo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.KafkaProducerService;

@RestController
public class MessageController {

    private final KafkaProducerService producerService;

    public MessageController(KafkaProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping("/send")
    public String sendMessage(@RequestBody String message, @RequestParam(required = false) String key) {
        // If no key is provided, use a default key or null
        if (key == null) {
            key = "default-key"; // Use a default key or null if not provided
        }
        
        producerService.sendMessage(message, key);
        return "Message sent: " + message + " with key: " + key;
    }
}
