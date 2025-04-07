package com.example.elasticsearch.demo;

import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import reactor.core.publisher.Flux;

public interface ProductRepository
        extends ReactiveElasticsearchRepository<Product, String> {

    Flux<Product> findByName(String name);
    Flux<Product> findByDescription(String description);
    Flux<Product> findByPriceBetween(double min, double max);
}