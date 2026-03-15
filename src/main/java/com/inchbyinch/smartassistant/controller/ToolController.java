package com.inchbyinch.smartassistant.controller;

import com.inchbyinch.smartassistant.service.ToolService;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tools")
public class ToolController {

    private final ToolService toolService;

    public ToolController(ToolService toolService) {
        this.toolService = toolService;
    }

    @GetMapping("chat")
    public ResponseEntity<String> chat(String userMessage, String userId) {
        return ResponseEntity.ok(toolService.chat(userMessage, userId));
    }

}
