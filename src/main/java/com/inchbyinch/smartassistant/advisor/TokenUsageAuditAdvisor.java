package com.inchbyinch.smartassistant.advisor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.model.ChatResponse;


public class TokenUsageAuditAdvisor implements CallAdvisor {

    private static final Logger logger = LoggerFactory.getLogger(TokenUsageAuditAdvisor.class);
    @Override
    public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain) {
        ChatClientResponse chatClientResponse =  callAdvisorChain.nextCall(chatClientRequest);
       ChatResponse chatResponse = chatClientResponse.chatResponse();

       if(chatResponse== null){
           return chatClientResponse;
       }
        Usage usage =  chatResponse.getMetadata().getUsage();
       if(usage == null){
           return chatClientResponse;
       }

        logger.info(
                "Token usage - prompt: {}, completion: {}, total: {}",
                usage.getPromptTokens(),
                usage.getCompletionTokens(),
                usage.getTotalTokens()
        );
       return chatClientResponse;
    }

    @Override
    public String getName() {
        return "TokenUsageAuditAdvisor";
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
