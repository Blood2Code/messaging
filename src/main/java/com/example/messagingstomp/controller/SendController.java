package com.example.messagingstomp.controller;

import com.example.messagingstomp.entity.Greeting;
import com.example.messagingstomp.entity.ProvideMessage;
import com.example.messagingstomp.component.EventProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SendController {

    private final EventProvider messageService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/sendTo")
    public void sendMessage(@RequestBody ProvideMessage provideMessage) {
        messageService.sendMessageToUser(provideMessage.getUsername(),
                new Greeting("Hi " + provideMessage.getUsername() + "," + provideMessage.getMessage()));
    }
}
