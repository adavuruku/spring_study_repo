package com.example.kafka_websocket.controller;

import com.example.kafka_websocket.model.ChatMessage;
import com.example.kafka_websocket.service.KafkaConsumerServices;
import com.example.kafka_websocket.service.KafkaProducerServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@Slf4j
public class WebSocketController {
    // using websocket and kafka for a distributed system
    // https://callmezydd.medium.com/kafka-and-websocket-integration-building-efficient-real-time-applications-with-seamless-4b1e88342478#:~:text=The%20integration%20of%20Kafka%20and,between%20the%20server%20and%20clients.
    private final KafkaProducerServices kafkaProducerServices;
    private final KafkaConsumerServices kafkaConsumerServices;

    public WebSocketController(KafkaProducerServices kafkaProducerServices, KafkaConsumerServices kafkaConsumerServices) {
        this.kafkaProducerServices = kafkaProducerServices;
        this.kafkaConsumerServices = kafkaConsumerServices;
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public void handleChatMessage(@Payload ChatMessage message) {
        // Send the message to Kafka
        log.info("Received message: {}", message);
        kafkaProducerServices.sendMessage(message);
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public void addUser(@Payload ChatMessage message, SimpMessageHeaderAccessor headerAccessor) {
        //add user to join room chat
        log.info("User added: {}", message.getSender());
        if (headerAccessor != null && headerAccessor.getSessionAttributes() != null) {
            headerAccessor.getSessionAttributes().put("username", message.getSender());
        } else {
            log.error("headerAccessor or session attributes is null.");
        }
        kafkaProducerServices.sendMessage(message);
    }

    @MessageMapping("/chat.removeUser")
    @SendTo("/topic/public")
    public void removeUser(@Payload ChatMessage message, SimpMessageHeaderAccessor headerAccessor) {
        // Menangani pengguna yang keluar (disconnect)
        log.info("User disconnected: {}", message.getSender());
        // Mengirim pesan ke topik "/topic/public"
        kafkaProducerServices.sendMessage(message);
        if (headerAccessor != null && headerAccessor.getSessionAttributes() != null) {
            // Hapus atribut username dari sesi
            headerAccessor.getSessionAttributes().remove("username");
        } else {
            log.error("headerAccessor or session attributes is null.");
        }
    }

    @GetMapping("/api/chat")
    public List<ChatMessage> getChatMessages() {
        // Ambil data chat dari Kafka dan kirimkan ke frontend
        return kafkaConsumerServices.getChatMessages();
    }




// ### using only websocket for implementation

    //    @MessageMapping("/chat.sendMessage")
//    @SendTo("/topic/public")
//    public ChatMessage handleChatMessage(@Payload ChatMessage message) {
//        log.info("Received message: {}", message);
//        return message;
//    }
//
//    @MessageMapping("/chat.addUser")
//    @SendTo("/topic/public")
//    public ChatMessage addUser(@Payload ChatMessage message, SimpMessageHeaderAccessor headerAccessor) {
//        log.info("User added: {}", message.getSender());
//        headerAccessor.getSessionAttributes().put("username", message.getSender());
//        return message;
//    }
//
//    @MessageMapping("/chat.removeUser")
//    @SendTo("/topic/public")
//    public ChatMessage removeUser(@Payload ChatMessage message, SimpMessageHeaderAccessor headerAccessor) {
//        log.info("User disconnected: {}", message.getSender());
//        headerAccessor.getSessionAttributes().remove("username");
//        return message;
//    }
    @GetMapping("/")
    public String chatPage() {
        return "test.html";
    }
}
