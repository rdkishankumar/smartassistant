package com.inchbyinch.smartassistant.service.impl;

import com.inchbyinch.smartassistant.service.PromptStuffingService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class PromptStuffingServiceImpl implements PromptStuffingService {

    @Value("classpath:/promptTemplates/userPromptTemplate.st")
    private Resource userPromptTemplate;

    @Value("classpath:/promptTemplates/systemPromptTemplate.st")
    private Resource systemPromptTemplate;

    private final ChatClient chatClient;

    public PromptStuffingServiceImpl(ChatClient chatClient) {
        this.chatClient = chatClient;
    }


    @Override
    public String systemPrompt(String customerName, String customerMessage) {

        return chatClient
                .prompt()
                .user(promptTemp ->
                        promptTemp.text(userPromptTemplate)
                                .param("customerName", customerName)
                                .param("customerMessage", customerMessage))
                .call()
                .content();
    }

    @Override
    public String userPrompt(String message) {
         return chatClient
                .prompt()
                // .advisors(new TokenUsageAuditAdvisor())
                .system(systemPromptTemplate)   // system prompt
                .user(message)                  // user message
                .call()
                .content();
    }
}
