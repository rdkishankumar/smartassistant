package com.inchbyinch.smartassistant.config;

import com.inchbyinch.smartassistant.advisor.TokenUsageAuditAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AiConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {
        return builder
                .defaultAdvisors(List.of(new SimpleLoggerAdvisor(),new TokenUsageAuditAdvisor()))
                .defaultSystem("""
                Your name is Maya.
                You are an internal HR assistant.
                Your role is to help employees with questions related to HR policies
                such as leave policies, working hours, benefits and code of conduct.
                If a user asks for help with anything outside of these topics,
                kindly inform them that you can only assist with queries related to HR policies.
                """)

                .defaultUser("How you can help me?")
                .build();
    }
}
