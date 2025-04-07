package com.example.elasticsearch.demo;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfig {

   /* @Bean
    public ElasticsearchClient elasticsearchClient() {
        RestClient restClient = RestClient.builder(
                new HttpHost("localhost", 9200)
        ).build();

        ElasticsearchTransport transport = new RestClientTransport(
                restClient,
                new JacksonJsonpMapper()
        );

        return new ElasticsearchClient(transport);
    }

    */
   @Bean
   public ElasticsearchClient elasticsearchClient() {
       RestClient restClient = RestClient.builder(
               new HttpHost("localhost", 9200)
       ).build();

       // Configure Jackson to handle Lombok and Java 8 types
       ObjectMapper objectMapper = new ObjectMapper()
               .registerModule(new JavaTimeModule());

       ElasticsearchTransport transport = new RestClientTransport(
               restClient,
               new JacksonJsonpMapper(objectMapper)
       );

       return new ElasticsearchClient(transport);
   }
}