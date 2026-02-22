package com.nagp.products.product;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;
import java.util.List;

@Data
@Document(collection = "products")
public class Product {

    @Id
    private String id;
    private String name;
    private String description;
    private List<String> tags;
    private String brand;
    private String category;
    private List<String> audience;
    private List<String> colors;
    private List<String> sizes;
    private List<ProductImage> images;
    private Price price;
    private Inventory inventory;
    private Instant createdAt = Instant.now();
}
