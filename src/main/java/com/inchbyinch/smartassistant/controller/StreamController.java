package com.inchbyinch.smartassistant.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.api.OllamaChatOptions;
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

    public StreamController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping(value = "/stream")
    public Flux<String> stream(@RequestParam("message") String message) {
        return chatClient.prompt()
                .system(systemPromptTemplate)
                .options(OllamaChatOptions.builder()
                .temperature(0.1)
                .build())
                .user(message)
                .stream().content()
                .filter(chunk -> !chunk.isBlank());
    }
}
