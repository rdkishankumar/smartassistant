package com.inchbyinch.smartassistant.service.impl;

import com.inchbyinch.smartassistant.service.ToolService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@Service
public class ToolServiceImpl implements ToolService {

    private final ChatClient chatClient;

    public ToolServiceImpl(@Qualifier("timeToolChatClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public String chat(String userMessage, String userId) {
        return chatClient
                .prompt()
                .advisors(tool -> tool.param(CONVERSATION_ID, userId))
                .user(userMessage)
                .call()
                .content();
    }
}
