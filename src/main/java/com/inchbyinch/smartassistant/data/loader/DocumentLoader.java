package com.inchbyinch.smartassistant.data.loader;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DocumentLoader {
    private static final Logger logger = LoggerFactory.getLogger(DocumentLoader.class);

    @Value("classpath:kishan_kr_resume.pdf")
    private Resource documentResource;

    private final VectorStore vectorStore;

    @Value("${spring.ai.openai.api-key}")
    private String apiKey;

    public DocumentLoader(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }


    @PostConstruct
    public void loadDocument() {

        // 1. Read PDF
        TikaDocumentReader reader = new TikaDocumentReader(documentResource);
        List<Document> documents = reader.get();

        // 2. Split into smaller chunks
        TokenTextSplitter splitter = TokenTextSplitter.builder()
                .withChunkSize(100)
                .withMaxNumChunks(400)
                .build();
        List<Document> doc = splitter.apply(documents);

        // 3. Store embeddings
        vectorStore.add(doc);

        System.out.println("Loaded chunks: " + doc.size());
    }




    @PostConstruct
    public void check() {
        logger.info("OpenAI key loaded: {}", apiKey.substring(0,10));
    }
}
