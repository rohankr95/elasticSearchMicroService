package com.example.demoelasticsearch.controller;

import com.example.demoelasticsearch.document.Product;

import com.example.demoelasticsearch.search.SearchRequestDto;
import com.example.demoelasticsearch.service.impl.ProductServiceImplEs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/db/product")
public class ProductControllerEs {

    @Autowired
    ProductServiceImplEs service;


    @PostMapping("/{productName}")
    public Product save(@PathVariable("productName") final String productName) {

        return service.findByProductName(productName);
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable final String id) {
        return service.getById(id);
    }

    @PostMapping
    public void index(@RequestBody final Product product){
        service.index(product);
    }

    @PostMapping("/search")
    public List<Product> search(@RequestBody final SearchRequestDto dto)
    {
        return service.search(dto);
    }



}
