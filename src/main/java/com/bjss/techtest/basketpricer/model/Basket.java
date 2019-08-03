package com.bjss.techtest.basketpricer.model;

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

    public double calculateBasketSubtotal(){
        double calculatedSubtotal = 0;
        for (final Product product : products) {
            calculatedSubtotal += product.getPrice();
        }
        return calculatedSubtotal;
    }

}
