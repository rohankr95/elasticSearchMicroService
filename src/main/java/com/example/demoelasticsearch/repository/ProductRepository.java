package com.example.demoelasticsearch.repository;

import com.example.demoelasticsearch.document.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ProductRepository extends ElasticsearchRepository<Product, String> {
    public Product findByProductName(String productName);
}
