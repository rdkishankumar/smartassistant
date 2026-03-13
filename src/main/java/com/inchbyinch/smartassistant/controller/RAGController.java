package com.inchbyinch.smartassistant.controller;

import com.inchbyinch.smartassistant.service.RAGService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class RAGController {

    private final RAGService ragService;

    public RAGController(RAGService ragService) {
        this.ragService = ragService;
    }

    @GetMapping("/chat")
    public ResponseEntity<String> chatWithDocument(@RequestHeader String userId, @RequestParam String message) {
        return ResponseEntity.ok(ragService.chatWithDocument(userId, message));
    }

    @GetMapping("/resume")
    public ResponseEntity<String> chatWithResumeDocument(@RequestHeader String userId, @RequestParam String message) {
        return ResponseEntity.ok(ragService.resumeReader(userId, message));
    }

    @GetMapping("/web-chat")
    public ResponseEntity<String> webSearchChat(@RequestHeader String userId, @RequestParam String message) {
        return ResponseEntity.ok(ragService.webSearchChat(userId, message));
    }

}
