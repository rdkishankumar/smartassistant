package com.inchbyinch.smartassistant.service;

public interface RAGService {

    String chatWithDocument(String userId, String message);
    String resumeReader(String userId, String message);
    String webSearchChat(String userId, String message);
}
