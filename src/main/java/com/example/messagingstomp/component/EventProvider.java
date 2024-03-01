package com.example.messagingstomp.component;


import com.example.messagingstomp.entity.Greeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;

import java.util.Objects;

@Component
public class EventProvider implements ApplicationListener<SessionConnectEvent> {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public void onApplicationEvent(SessionConnectEvent event) {
        StompHeaderAccessor stompHeaderAccessor = StompHeaderAccessor.wrap(event.getMessage());
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(stompHeaderAccessor.getSessionId());
        headerAccessor.setLeaveMutable(true);

        String username = Objects.requireNonNull(event.getUser()).getName();

        messagingTemplate.convertAndSend("/user/" + username, "Welcome, " + username + "!");
    }

    public void sendMessageToUser(String username, Greeting message) {
        messagingTemplate.convertAndSend("/user/" + username, message);
    }

    public void sendMessageToTopic(String topic, String message) {
        messagingTemplate.convertAndSend("/topic/" + topic, message);
    }
}
