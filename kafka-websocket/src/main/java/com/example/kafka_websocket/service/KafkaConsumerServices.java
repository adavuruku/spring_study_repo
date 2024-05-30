package com.example.kafka_websocket.service;

import com.example.kafka_websocket.model.ChatMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class KafkaConsumerServices {
    private static final String TOPIC = "public-chats"; // Ganti dengan nama topik yang Anda inginkan
    private final SimpMessagingTemplate messagingTemplate;
    private final List<ChatMessage> chatMessages = new ArrayList<>();

    public KafkaConsumerServices(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @KafkaListener(topics = TOPIC, groupId = "my-group")
    public void handleMessage(String message) {
        log.info("Received message from Kafka: {}", message);
        try {
            ChatMessage chatMessage = new ObjectMapper().readValue(message, ChatMessage.class);

            chatMessages.add(chatMessage);
            messagingTemplate.convertAndSend("/topic/public", chatMessage);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ChatMessage> getChatMessages() {
        // Implementasikan logika untuk mengembalikan daftar pesan dari Kafka
        return new ArrayList<>(chatMessages);
    }
}
