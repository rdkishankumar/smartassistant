package com.inchbyinch.smartassistant.config;

import com.inchbyinch.smartassistant.advisor.TokenUsageAuditAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AiConfig {

    @Value("classpath:/promptTemplates/systemHealthPromptTemplate.st")
    private Resource systemHealthPromptTemplate;
    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {
        return builder
                .defaultAdvisors(List.of(new SimpleLoggerAdvisor(),new TokenUsageAuditAdvisor()))
                .defaultSystem(systemHealthPromptTemplate)
                .defaultUser("How you can help me?")
                .build();
    }
}
