package com.nagp.products.product;

import lombok.Data;

@Data
public class Price {
    private double amount;
    private String currency;
    private Double discountPrice;
}
