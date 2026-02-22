package com.nagp.products.product;

import lombok.Data;

@Data
public class ProductImage {
    private String url;
    private String type;
    private Integer order;
    private Boolean primary;
}
