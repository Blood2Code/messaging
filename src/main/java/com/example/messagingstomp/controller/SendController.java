package com.example.messagingstomp.controller;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class SendController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public SendController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/sendTo/{username}")
    public void sendMessage(@PathVariable("username") String username) {
        String destination = "/topic/greetings",
                message = "Test Umar";
        simpMessagingTemplate.convertAndSendToUser(username, destination, message);
    }
}
