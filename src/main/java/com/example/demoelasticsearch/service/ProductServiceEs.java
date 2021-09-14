package com.example.demoelasticsearch.service;

import com.example.demoelasticsearch.document.Product;
import com.example.demoelasticsearch.search.SearchRequestDto;

import java.util.List;

public interface ProductServiceEs {
    public void save(final Product product);
    public Product findById(final String id);
    public List<Product> search(final SearchRequestDto dto);
    public Boolean index(Product product);
    public Product getById(String id);
    public Product findByProductName(String productName);
    public void update(final Product product);
    public void delete(final String id);
}
