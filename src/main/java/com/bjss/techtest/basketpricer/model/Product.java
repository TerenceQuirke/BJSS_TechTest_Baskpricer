package com.bjss.techtest.basketpricer.model;

import java.math.BigDecimal;

public class Product {
    private ProductType name;
    private BigDecimal price;
    public Product(ProductType name, BigDecimal price)
    {
        setName(name);
        setPrice(price);
    }
    public ProductType getName(){
        return name;
    }

    public BigDecimal getPrice(){
        return price;
    }

    public void setName(ProductType name){
        this.name = name;
    }

    public void setPrice(BigDecimal price){
        this.price = price;
    }
}
