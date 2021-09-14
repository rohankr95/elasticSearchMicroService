package com.example.demoelasticsearch.kafka;

//import com.example.Cart.entity.User;
import com.example.demoelasticsearch.document.Product;
import com.example.demoelasticsearch.service.ProductServiceEs;
import com.example.demoelasticsearch.service.impl.ProductServiceImplEs;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
@Service
public class Kafka {
    @Autowired
    ProductServiceEs productServiceEs;
    @KafkaListener(topics = "product", groupId = "group")
    public void consume(String message){
        Gson g = new Gson();
        Product s = g.fromJson(message, Product.class);
        System.out.println(message);
        productServiceEs.save(s);
    }
    @KafkaListener(topics = "product-update", groupId = "group")
    public void consume1(String message){
        Gson g = new Gson();
        Product s = g.fromJson(message, Product.class);
        System.out.println(message);
        productServiceEs.update(s);
    }
    @KafkaListener(topics = "product-delete", groupId = "group")
    public void consume2(String message){
        System.out.println(message);
        productServiceEs.delete(message);
    }
}
