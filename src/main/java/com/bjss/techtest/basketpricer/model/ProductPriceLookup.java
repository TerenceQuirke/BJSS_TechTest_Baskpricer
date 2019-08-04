package com.bjss.techtest.basketpricer.model;

import java.math.BigDecimal;

public class ProductPriceLookup {
    private Product product;
    private static final BigDecimal breadPrice = BigDecimal.valueOf(0.8);
    private static final BigDecimal milkPrice = BigDecimal.valueOf(1.3);
    private static final BigDecimal soupPrice = BigDecimal.valueOf(0.65);
    private static final BigDecimal applesPrice = BigDecimal.valueOf(1);

    public Product ProductWithPrice(ProductType productType){
        BigDecimal productPrice = BigDecimal.ZERO;

        switch (productType) {
            case Bread:
                productPrice = breadPrice;
                break;
            case Milk:
                productPrice = milkPrice;
                break;
            case Soup:
                productPrice = soupPrice;
                break;
            case Apples:
                productPrice = applesPrice;
                break;
            default:
                productPrice = BigDecimal.ZERO;
                break;
        }
        product = new Product(productType, productPrice);
        return product;
    }
}

