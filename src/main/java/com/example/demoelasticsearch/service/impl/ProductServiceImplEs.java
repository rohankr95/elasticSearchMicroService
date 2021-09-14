package com.example.demoelasticsearch.service.impl;

import com.example.demoelasticsearch.document.Product;
import com.example.demoelasticsearch.helper.Indices;
import com.example.demoelasticsearch.repository.ProductRepository;
import com.example.demoelasticsearch.search.SearchRequestDto;
import com.example.demoelasticsearch.search.util.SearchUtil;
import com.example.demoelasticsearch.service.ProductServiceEs;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ProductServiceImplEs implements ProductServiceEs {
   @Autowired
    private ProductRepository repository;

    public void save(final Product product) {

        repository.save(product);
    }
    public void update(final Product product){
        repository.save(product);
    }
    public void delete(final String id){
        repository.deleteById(id);
    }
    public Product findById(final String id) {
        return repository.findById(id).orElse(null);
    }
    private static final ObjectMapper MAPPER= new ObjectMapper();
    private static final org.slf4j.Logger LOG =LoggerFactory.getLogger(ProductServiceEs.class);
    @Autowired
    private final RestHighLevelClient client;
    @Autowired
    public ProductServiceImplEs(RestHighLevelClient client)
    {
        this.client=client;
    }
    public List<Product> search(final SearchRequestDto dto) {
        final SearchRequest searchRequest = SearchUtil.buildSearchRequest(Indices.PRODUCT_INDEX, dto);
        if(searchRequest == null){
            LOG.error("Failed to build search request");
            return Collections.emptyList();
        }
        try{
            final SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            final SearchHit[] searchHits = searchResponse.getHits().getHits();
            final List<Product> products = new ArrayList<>(searchHits.length);
            for(SearchHit hit : searchHits){
                MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                products.add(MAPPER.readValue(hit.getSourceAsString(), Product.class));
            }
            return products;
        }catch (Exception e){
            LOG.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }
    public List<Product> searchCreatedSince(final SearchRequestDto dto) {
        final SearchRequest request = SearchUtil.buildSearchRequest(
                Indices.PRODUCT_INDEX,
                dto
        );
        return searchInternal(request);
    }
    private List<Product> searchInternal(final SearchRequest request) {
        if (request == null) {
            LOG.error("Failed to build search request");
            return Collections.emptyList();
        }
        try {
            final SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            final SearchHit[] searchHits = response.getHits().getHits();
            final List<Product> products = new ArrayList<>(searchHits.length);
            for (SearchHit hit : searchHits) {
                MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
                products.add(
                        MAPPER.readValue(hit.getSourceAsString(), Product.class)
                );
            }
            return products;
        } catch (Exception e) {
            System.out.println("Error in searching");
            e.printStackTrace();
            LOG.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }
    @Override
    public Boolean index(final Product product) {
        try {
            final String productAsString = MAPPER.writeValueAsString(product);
            final IndexRequest request = new IndexRequest(Indices.PRODUCT_INDEX);
            request.id(product.getProductId());
            request.source(productAsString, XContentType.JSON);
            final IndexResponse response = client.index(request, RequestOptions.DEFAULT);
            return response != null && response.status().equals(RestStatus.OK);
        }
        catch (final Exception e) {
            LOG.error(e.getMessage(), e);
            return false;
        }
    }
    @Override
    public Product getById(final String productId) {
        try {
            final GetResponse documentFields = client.get(
                    new GetRequest(Indices.PRODUCT_INDEX, productId),
                    RequestOptions.DEFAULT
            );
            if (documentFields == null || documentFields.isSourceEmpty()) {
                return null;
            }
            return MAPPER.readValue(documentFields.getSourceAsString(), Product.class);
        } catch (final Exception e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }
    @Override
    public Product findByProductName(String productName) {
        return repository.findByProductName(productName);
    }
}
