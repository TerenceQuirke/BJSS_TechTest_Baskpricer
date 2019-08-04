package com.bjss.techtest.basketpricer.service;

import com.bjss.techtest.basketpricer.model.Product;
import com.bjss.techtest.basketpricer.model.ProductType;

import java.util.LinkedList;
import java.util.List;

public class BasketRequest {
    private List<ProductType> products = new LinkedList<ProductType>();

    public List<ProductType> getProducts()
    {
        return products;
    }
}
