package com.inchbyinch.smartassistant.service.impl;

import com.inchbyinch.smartassistant.service.MessageChatMemoryService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@Service
public class MessageChatMemoryServiceImpl implements MessageChatMemoryService {

    private final ChatClient chatMemoryConfig;
    public MessageChatMemoryServiceImpl(@Qualifier("chatClientWithMemory") ChatClient chatMemoryConfig) {
        this.chatMemoryConfig = chatMemoryConfig;
    }
    @Override
    public String chat(String message, String userId) {

        return chatMemoryConfig
                .prompt()
                .user(message)
                .advisors(advisorspec->
                        advisorspec.param(CONVERSATION_ID, userId))
                .call()
                .content();

    }
}
