package com.example.kafka_websocket.service;

import com.example.kafka_websocket.model.ChatMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerServices {
    private static final String TOPIC = "public-chats"; // Ganti dengan nama topik yang Anda inginkan
    private final KafkaTemplate<String, String> kafkaTemplate;
    public KafkaProducerServices(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    public void sendMessage(ChatMessage message) {
        try {
            kafkaTemplate.send(TOPIC, new ObjectMapper().writeValueAsString(message) );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
