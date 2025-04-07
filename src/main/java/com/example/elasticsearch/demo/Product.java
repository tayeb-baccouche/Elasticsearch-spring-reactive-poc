package com.example.elasticsearch.demo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.WriteTypeHint;

@Data
@Document(indexName = "product", writeTypeHint = WriteTypeHint.FALSE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    private String id;
    @Field(type = FieldType.Text)
    private String name;
    @Field(type = FieldType.Double)
    private Double price;
    @Field(type = FieldType.Text)
    private String description;


   }
