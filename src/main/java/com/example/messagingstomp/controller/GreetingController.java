package com.example.messagingstomp.controller;

import com.example.messagingstomp.entity.Greeting;
import com.example.messagingstomp.entity.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {


    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(Message message) {
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }


//    @EventListener(SessionConnectedEvent.class)
//    public void handleConnected(WebSocketSession session) {
//        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        System.err.println(username);
//        String channelName = "private-" + username;
//
//        userChannels.put(session.getId(), channelName);
//        messagingTemplate.convertAndSendToUser(username, "/topic/" + channelName, null);
//    }
}
