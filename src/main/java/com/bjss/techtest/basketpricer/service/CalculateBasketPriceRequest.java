package com.bjss.techtest.basketpricer.service;

import java.util.*;

import com.bjss.techtest.basketpricer.model.ProductType;
import com.fasterxml.jackson.annotation.*;



public class CalculateBasketPriceRequest {
    private String[] productList;

    @JsonProperty("ProductList")
    public String[] getProductList() { return productList; }
    @JsonProperty("ProductList")
    public void setProductList(String[] value) { this.productList = value; }
}
