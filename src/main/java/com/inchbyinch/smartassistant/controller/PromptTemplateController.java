package com.inchbyinch.smartassistant.controller;

import com.inchbyinch.smartassistant.service.PromptStuffingService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class PromptTemplateController {

    private final PromptStuffingService promptStuffingService;

    public PromptTemplateController(PromptStuffingService promptStuffingService) {
        this.promptStuffingService = promptStuffingService;
    }

    @GetMapping("email")
    public ResponseEntity<String> emailAssistant(@RequestParam("customerName") String customerName,
                                         @RequestParam String customerMessage) {
       return  ResponseEntity.ok()
                        .body(promptStuffingService.systemPrompt(customerName, customerMessage));
    }

    @GetMapping("/prompt-stuffing")
    public ResponseEntity<String> askHrQuestion(@RequestParam String message) {
        return ResponseEntity.ok()
                .body(promptStuffingService.userPrompt(message));
    }
}
