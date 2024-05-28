package com.example.kafka_websocket.service;

import com.example.kafka_websocket.model.ChatMessage;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerServices {
    private static final String TOPIC = "public-chats"; // Ganti dengan nama topik yang Anda inginkan
    private final KafkaTemplate<String, ChatMessage> kafkaTemplate;
    public KafkaProducerServices(KafkaTemplate<String, ChatMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    public void sendMessage(ChatMessage message) {
        kafkaTemplate.send(TOPIC, message);
    }
}
