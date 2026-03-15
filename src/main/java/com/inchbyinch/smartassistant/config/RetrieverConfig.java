package com.inchbyinch.smartassistant.config;

import com.inchbyinch.smartassistant.advisor.TokenUsageAuditAdvisor;
import com.inchbyinch.smartassistant.data.loader.WebSearchDocumentRetriever;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.preretrieval.query.transformation.TranslationQueryTransformer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestClient;

import java.util.List;

@Configuration
public class RetrieverConfig {

    @Bean
    public WebSearchDocumentRetriever webSearchRetriever(
            RestClient.Builder restClientBuilder,
            @Value("${tavily.api.key}") String apiKey,
            @Value("${tavily.base.url}") String baseUrl,
            @Value("${tavily.default.result.limit:5}") int limit
    ) {

        return WebSearchDocumentRetriever.builder()
                .restClientBuilder(restClientBuilder)
                .apiKey(apiKey)
                .baseUrl(baseUrl)
                .maxResults(limit)
                .build();
    }

    @Bean("webSearchRAGChatClient")
    public ChatClient chatClient(
            ChatClient.Builder builder,
            ChatMemory chatMemory,
            WebSearchDocumentRetriever retriever
    ) {

        Advisor loggerAdvisor = new SimpleLoggerAdvisor();
        Advisor tokenUsageAdvisor = new TokenUsageAuditAdvisor();
        Advisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();

        var ragAdvisor = RetrievalAugmentationAdvisor.builder()
                .queryTransformers(TranslationQueryTransformer.builder()
                .chatClientBuilder(builder.clone())
                .targetLanguage("english").build())
                .documentRetriever(retriever)
                .build();

        return builder
                .defaultSystem("""
                        You are an assistant that answers questions using retrieved web results.
                        Always prioritize the provided context.
                        If context exists, do not say you lack real-time information.
                        """)
                .defaultAdvisors(
                        List.of(
                                loggerAdvisor,
                                memoryAdvisor,
                                tokenUsageAdvisor,
                                ragAdvisor
                        )
                )
                .build();
    }


}