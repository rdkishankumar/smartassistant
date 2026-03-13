package com.inchbyinch.smartassistant.controller;

import com.inchbyinch.smartassistant.service.MessageChatMemoryService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/")
@RestController
public class ChatMemoryController {

    private final MessageChatMemoryService messageChatMemoryService;

    public ChatMemoryController(MessageChatMemoryService messageChatMemoryService) {
        this.messageChatMemoryService = messageChatMemoryService;
    }

    @GetMapping("chat-memory")
    public ResponseEntity<String> getChatMemory(@RequestHeader String userId,
                                                @RequestParam String message) {
        return ResponseEntity.ok(messageChatMemoryService.chat(message, userId));
    }
}
