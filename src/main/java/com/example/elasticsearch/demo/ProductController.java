package com.example.elasticsearch.demo;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/search")
    public Flux<Product> fullTextSearch(@RequestParam String query) {
        return productService.fullTextSearch(query);
    }

    @GetMapping("/fuzzy")
    public Flux<Product> fuzzySearch(@RequestParam String term) {
        return productService.fuzzySearch(term);
    }

    @PostMapping
    public Flux<Product> createProduct(@RequestBody Product product) {
        return productService.createIndex()
                .thenMany(productService.getProductsByName(product.getName()));
    }
}