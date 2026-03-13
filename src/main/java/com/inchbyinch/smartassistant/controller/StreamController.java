package com.inchbyinch.smartassistant.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api")
public class StreamController {

    @Value("classpath:/promptTemplates/systemPromptTemplate.st")
    private Resource systemPromptTemplate;
    private final ChatClient chatClient;

    public StreamController(@Qualifier("systemChatClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping(value = "/stream")
    public Flux<String> stream(@RequestParam("message") String message) {
        return chatClient.prompt()
                .system(systemPromptTemplate)
                .user(message)
                .options(OpenAiChatOptions.builder()
                        .temperature(0.1)
                        .build())
                .stream()
                .content()
                .filter(chunk -> !chunk.isBlank());
    }
}
