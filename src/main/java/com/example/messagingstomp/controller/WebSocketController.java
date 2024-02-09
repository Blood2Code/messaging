package com.example.messagingstomp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    private final SimpMessageSendingOperations messagingTemplate;
    @Autowired
    public WebSocketController(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @GetMapping("/connectSessions")
    public ResponseEntity<String> connectSessions() {
        // Пример вызова метода подключения к каналу для двух сессий
        messagingTemplate.convertAndSend("/app/connect/{channel}", "your_channel");
        return ResponseEntity.ok("Connecting sessions to channel");
    }

    @GetMapping("/sendMessage")
    public ResponseEntity<String> sendMessage() {
        // Пример вызова метода отправки сообщения для двух сессий
        messagingTemplate.convertAndSend("/app/sendMessage/{channel}", "your_channel");
        return ResponseEntity.ok("Sending message to channel");
    }
}