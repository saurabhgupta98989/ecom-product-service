package com.nagp.products.events;

import com.nagp.products.product.Inventory;
import com.nagp.products.product.Price;
import com.nagp.products.product.Product;
import com.nagp.products.product.ProductImage;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;


@Data
@Builder
public class ProductCreatedEvent  {
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
