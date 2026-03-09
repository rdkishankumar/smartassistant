package com.inchbyinch.smartassistant.controller;

import com.inchbyinch.smartassistant.model.CountryCities;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api")
public class StructureOutputController {
    private final ChatClient chatClient;

    public StructureOutputController(ChatClient.Builder chatClient) {
        this.chatClient = chatClient
                .defaultAdvisors(List.of(new SimpleLoggerAdvisor())).build();
    }

    @RequestMapping("/structure")
    public ResponseEntity<CountryCities> countryCities(
            @RequestParam("message") String message) {

        CountryCities response = chatClient
                .prompt()
                .user(message)
                .options(OllamaChatOptions.builder()
                        .temperature(0.1)
                        .build())
                .call()
                .entity(CountryCities.class);

        return ResponseEntity.ok(response);
    }

}
