package com.inchbyinch.smartassistant.service.impl;

import com.inchbyinch.smartassistant.service.RAGService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@Service
public class RAGServiceImpl implements RAGService {

    @Value("classpath:/promptTemplates/systemRandomDataTemplate.st")
    private Resource docPromptTemplate;

    @Value("classpath:/promptTemplates/systemPromptResume.st")
    private Resource resPromptTemplate;

    private final ChatClient chatClient;
    private final VectorStore vectorStore;
    ChatClient webSearchRAGChatClient;

    public RAGServiceImpl(@Qualifier("chatClientWithMemory") ChatClient chatClient,
                          VectorStore vectorStore,
                          @Qualifier("webSearchRAGChatClient") ChatClient webSearchRAGChatClient) {
        this.chatClient = chatClient;
        this.vectorStore = vectorStore;
        this.webSearchRAGChatClient = webSearchRAGChatClient;

    }

    @Override
    public String chatWithDocument(String userId, String message) {
        SearchRequest searchRequest = SearchRequest.builder()
                .query(message)
                .topK(3)
                .similarityThreshold(0.7)
                .build();
        List<Document> documentList = vectorStore.similaritySearch(searchRequest);

        String similarContext = documentList.stream()
                .map(Document::getText)
                .collect(Collectors.joining(System.lineSeparator()));

        return chatClient.prompt()
                .system(promptSystemSpec ->
                        promptSystemSpec.text(docPromptTemplate)
                                .param("documents", similarContext))
                .advisors(advisorSpec ->
                        advisorSpec.param(CONVERSATION_ID, userId))
                .user(message)
                .call().content();
    }

    @Override
    public String resumeReader(String userId, String message) {

        /*SearchRequest searchRequest = SearchRequest.builder()
                .query(message)
                .topK(4)
                .similarityThreshold(0.5)
                .build();

        List<Document> documentList = vectorStore.similaritySearch(searchRequest);

        String contextData = documentList.stream()
                .map(Document::getText)
                .collect(Collectors.joining(System.lineSeparator()));*/

       return  chatClient.prompt()
                /*.system(promptSystemSpec -> promptSystemSpec.text(resPromptTemplate)
                        .param("documents", contextData))*/
                .advisors(advisorSpec -> advisorSpec
                        .param(CONVERSATION_ID, userId))
                .user(message)
                .call()
                .content();
    }

    @Override
    public String webSearchChat(String userId, String message) {
        return webSearchRAGChatClient.prompt()
                .advisors(advisorSpec ->
                        advisorSpec.param(CONVERSATION_ID, userId))
                .user(message)
                .call().content();
    }
}
