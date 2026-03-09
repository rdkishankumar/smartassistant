package com.inchbyinch.smartassistant.service;

public interface PromptStuffingService {
    String systemPrompt(String customerName, String customerMessage);
    String userPrompt(String message);
}
