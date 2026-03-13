package com.inchbyinch.smartassistant.data.loader;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.rag.Query;
import org.springframework.ai.rag.retrieval.search.DocumentRetriever;
import org.springframework.http.HttpHeaders;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WebSearchDocumentRetriever implements DocumentRetriever {

    private static final Logger log =
            LoggerFactory.getLogger(WebSearchDocumentRetriever.class);

    private final RestClient restClient;
    private final int resultLimit;

    private WebSearchDocumentRetriever(
            RestClient.Builder restClientBuilder,
            String apiKey,
            String baseUrl,
            int resultLimit
    ) {

        Assert.notNull(restClientBuilder, "RestClient builder must not be null");
        Assert.hasText(apiKey, "API key must not be empty");
        Assert.hasText(baseUrl, "Base URL must not be empty");
        Assert.isTrue(resultLimit > 0, "resultLimit must be > 0");

        this.resultLimit = resultLimit;

        this.restClient = restClientBuilder
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .build();
    }

    @Override
    public List<Document> retrieve(Query query) {

        Assert.notNull(query, "Query must not be null");
        Assert.hasText(query.text(), "Query text must not be empty");

        log.info("Executing Tavily search for query: {}", query.text());

        try {

            TavilyResponsePayload response =
                    restClient.post()
                            .uri("/search")
                            .body(new TavilyRequestPayload(query.text(), "advanced", resultLimit))
                            .retrieve()
                            .body(TavilyResponsePayload.class);

            if (response == null || CollectionUtils.isEmpty(response.results())) {
                log.warn("No results returned from Tavily");
                return Collections.emptyList();
            }

            List<Document> documents = response.results()
                    .stream()
                    .filter(hit -> hit != null && hit.content() != null && !hit.content().isBlank())
                    .map(hit -> Document.builder()
                            .text(hit.content().trim())
                            .metadata("title", hit.title() != null ? hit.title() : "")
                            .metadata("url", hit.url() != null ? hit.url() : "")
                            .score(hit.score() != null ? hit.score() : 0.0)
                            .build()
                    )
                    .toList();

            log.debug("Retrieved {} documents from Tavily", documents.size());
            return documents;

        } catch (Exception ex) {
            log.error("Tavily search failed", ex);
            return Collections.emptyList();
        }
    }

    // -------- Request --------

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    record TavilyRequestPayload(
            String query,
            String searchDepth,
            int maxResults
    ) {}

    // -------- Response --------

    record TavilyResponsePayload(List<Hit> results) {

        record Hit(
                String title,
                String url,
                String content,
                Double score
        ) {}
    }

    // -------- Builder --------

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private RestClient.Builder restClientBuilder;
        private String apiKey;
        private String baseUrl;
        private int resultLimit = 5;

        public Builder restClientBuilder(RestClient.Builder builder) {
            this.restClientBuilder = builder;
            return this;
        }

        public Builder apiKey(String apiKey) {
            this.apiKey = apiKey;
            return this;
        }

        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder maxResults(int maxResults) {
            this.resultLimit = maxResults;
            return this;
        }

        public WebSearchDocumentRetriever build() {

            Assert.notNull(restClientBuilder, "RestClient builder required");
            Assert.hasText(apiKey, "API key required");
            Assert.hasText(baseUrl, "Base URL required");

            return new WebSearchDocumentRetriever(
                    restClientBuilder,
                    apiKey,
                    baseUrl,
                    resultLimit
            );
        }
    }

}