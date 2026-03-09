package com.inchbyinch.smartassistant.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class PromptTemplateController {

    @Value("classpath:/promptTemplates/userPromptTemplate.st")
    private Resource userPromptTemplate;

    @Value("classpath:/promptTemplates/systemPromptTemplate.st")
    private Resource systemPromptTemplate;

    private final ChatClient chatClient;

    public PromptTemplateController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("email")
    public String emailAssistant(@RequestParam("customerName") String customerName,
                                 @RequestParam String customerMessage) {
        return chatClient
                .prompt()
                .user(promptTemp ->
                        promptTemp.text(userPromptTemplate)
                                .param("customerName", customerName)
                                .param("customerMessage", customerMessage))
                .call()
                .content();
    }

    @GetMapping("/prompt-stuffing")
    public String askHrQuestion(@RequestParam String message) {
        return chatClient
                .prompt()
                // .advisors(new TokenUsageAuditAdvisor())
                .system(systemPromptTemplate)   // system prompt
                .user(message)                  // user message
                .call()
                .content();
    }
}
