package com.inchbyinch.smartassistant.config;

import com.inchbyinch.smartassistant.tools.ToolsTime;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class TimeToolsConfig {

    @Bean("timeToolChatClient")
    public ChatClient timeToolChatClient(
            ChatClient.Builder chatClientBuilder,
            ChatMemory chatMemory,
            ToolsTime toolsTime) {

        MessageChatMemoryAdvisor memoryAdvisor =
                MessageChatMemoryAdvisor.builder(chatMemory).build();

        return chatClientBuilder
                .defaultAdvisors(List.of(memoryAdvisor))
                .defaultTools(toolsTime)
                .build();
    }
}
