package com.example.messagingstomp.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@Slf4j
public class WebSocketEventListener {

    private int activeSessions = 0;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        activeSessions++;
        System.out.println("New WebSocket connection. Total active sessions: " + activeSessions + " | " + event.getUser() + " | " + event.getMessage());
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        if (headerAccessor.getSessionAttributes().containsKey("sessionId")) {
            activeSessions--;
            System.out.println("WebSocket connection closed. Total active sessions: " + activeSessions);
        }
    }
}