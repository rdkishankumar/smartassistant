package com.inchbyinch.smartassistant.controller;

import com.inchbyinch.smartassistant.model.CountryCities;
import com.inchbyinch.smartassistant.service.StructuredOutputService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class StructureOutputController {

    private final StructuredOutputService structuredOutputService;

    public StructureOutputController(StructuredOutputService structuredOutputService) {
        this.structuredOutputService = structuredOutputService;
    }

    @GetMapping("/structure")
    public ResponseEntity<CountryCities> chatBean(
            @RequestParam("message") String message) {
        return ResponseEntity.ok()
                .body(structuredOutputService.structuredOutput(message));
    }

    @GetMapping("/chat-list")
    public ResponseEntity<List<String>> chatList(
            @RequestParam("message") String message) {
        return ResponseEntity.ok()
                .body(structuredOutputService.chatListOutput(message));
    }

    @GetMapping("/chat-map")
    public ResponseEntity<Map<String, Object>> chatMap(
            @RequestParam("message") String message) {
        return ResponseEntity.ok()
                .body(structuredOutputService.chatMapOutput(message));
    }

}
