package com.example.messagingstomp.handler;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;
@Controller
public class WebSocketHandler extends TextWebSocketHandler {

    private final SimpMessageSendingOperations messagingTemplate;

    public WebSocketHandler(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Логика, выполняемая после установки соединения
        super.afterConnectionEstablished(session);
    }

    @MessageMapping("/connect/{channel}")
    public void connectToChannel(@Payload String channel, WebSocketSession session) throws Exception {
        // Логика подключения к каналу
        // В данном случае, просто отправим сообщение об успешном подключении
        messagingTemplate.convertAndSend("/topic/" + channel, "Connected to channel: " + channel);
    }

    @MessageMapping("/sendMessage/{channel}")
    public void sendMessageToChannel(@Payload String message, @Payload String channel, WebSocketSession session) throws Exception {
        // Логика отправки сообщения в канал
        messagingTemplate.convertAndSend("/topic/" + channel, session.getId() + ": " + message);
    }
}