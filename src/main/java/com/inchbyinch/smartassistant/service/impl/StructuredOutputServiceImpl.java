package com.inchbyinch.smartassistant.service.impl;

import com.inchbyinch.smartassistant.model.CountryCities;
import com.inchbyinch.smartassistant.service.StructuredOutputService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.ai.converter.MapOutputConverter;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StructuredOutputServiceImpl implements StructuredOutputService {

    private final ChatClient chatClient;

    public StructuredOutputServiceImpl(ChatClient.Builder chatClient) {
        this.chatClient = chatClient.defaultAdvisors(List.of(new SimpleLoggerAdvisor())).build();
    }
    @Override
    public CountryCities structuredOutput(String message) {
        return (CountryCities) chatClient
                .prompt()
                .user(message)
                .options(OpenAiChatOptions.builder()
                        .temperature(0.1)
                        .build())
                .call()
                .entity(CountryCities.class);

    }

    @Override
    public List<String> chatListOutput(String message) {
          return chatClient
                .prompt()
                .user(message)
                .options(OpenAiChatOptions.builder()
                        .temperature(0.1)
                        .build())
                .call()
                .entity(new ListOutputConverter());
    }

    @Override
    public Map<String, Object> chatMapOutput(String message) {
        return chatClient.prompt()
                .user(message)
                .options(OpenAiChatOptions.builder()
                        .temperature(0.7)
                        .build())
                .call()
                .entity(new MapOutputConverter());
    }
}
