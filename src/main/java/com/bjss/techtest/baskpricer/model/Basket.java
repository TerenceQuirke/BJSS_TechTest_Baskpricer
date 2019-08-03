package com.bjss.techtest.baskpricer.model;

import java.util.LinkedList;
import java.util.List;

public class Basket {
    private List<Product> products = new LinkedList<Product>();

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> items) {
        this.products = items;
    }

    public void addProduct(Product item) {
        products.add(item);
    }

}
