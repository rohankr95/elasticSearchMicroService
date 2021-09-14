package com.example.demoelasticsearch.document;


import com.example.demoelasticsearch.helper.Indices;
        import org.springframework.data.annotation.Id;
        import org.springframework.data.elasticsearch.annotations.Document;
        import org.springframework.data.elasticsearch.annotations.Field;
        import org.springframework.data.elasticsearch.annotations.FieldType;
        import org.springframework.data.elasticsearch.annotations.Setting;
        import org.springframework.data.elasticsearch.client.reactive.ReactiveElasticsearchClient;

@Document(indexName = Indices.PRODUCT_INDEX)
@Setting(settingPath = "static/es-settings.json")
public class Product {

    @Id
    @Field(type = FieldType.Keyword)
    private String id;
    @Field(type = FieldType.Text)
    private String productDescription;
    @Field(type = FieldType.Float)
    private float price;
    @Field(type = FieldType.Float)
    private float warranty;
    @Field(type = FieldType.Integer)
    private int rating;
    @Field(type = FieldType.Text)
    private String categoryName;
    @Field(type = FieldType.Text)
    private String productName;

    public String getProductId() {
        return id;
    }

    public void setProductId(String productId) {
        this.id = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public double getWarranty() {
        return warranty;
    }

    public void setWarranty(float warranty) {
        this.warranty = warranty;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
