package com.example.elasticsearch.demo;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.client.elc.ReactiveElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ReactiveIndexOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ElasticsearchClient elasticsearchClient;
    private final ReactiveElasticsearchTemplate reactiveTemplate;

    // Basic Repository Methods
    public Flux<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    // Advanced Search using New Client
    public Flux<Product> fullTextSearch(String query) {
        Query queryObj = Query.of(q -> q
                .multiMatch(m -> m
                        .query(query)
                        .fields("name", "description")
                )
        );

        return executeSearch(queryObj);
    }

    public Flux<Product> fuzzySearch(String term) {
        Query queryObj = Query.of(q -> q
                .fuzzy(f -> f
                        .field("name")
                        .value(term)
                        .fuzziness("AUTO")
                )
        );

        return executeSearch(queryObj);
    }

    private Flux<Product> executeSearch(Query query) {
        try {
            SearchResponse<Product> response = elasticsearchClient.search(
                    SearchRequest.of(s -> s
                            .index("product")
                            .query(query)
                    ), Product.class
            );

            return Flux.fromIterable(response.hits().hits())
                    .map(Hit::source)
                    .filter(Objects::nonNull);

        } catch (IOException e) {
            return Flux.error(new RuntimeException("Search failed", e));
        }
    }

    // Index Management Example
    public Mono<Boolean> createIndex() {
        ReactiveIndexOperations indexOps = reactiveTemplate.indexOps(Product.class);
        return indexOps.create();
    }
}