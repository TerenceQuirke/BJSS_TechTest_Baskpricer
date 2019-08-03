package com.bjss.techtest.baskpricer.controller;

import com.bjss.techtest.baskpricer.model.Basket;
import com.bjss.techtest.baskpricer.model.Product;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PriceBasketController {
    private static final String template = "Subtotal: %1$s! \n\r%2s!\n\rTotal price: %3s!";

    @RequestMapping("/basketpricer/basketcontents")
    public List<Product> submitBasket(){
        System.out.println("In submitBasket");
        Basket basket = new Basket();
        basket.addProduct(new Product("apples", 1));
        basket.addProduct(new Product("soup", 0.65));
        basket.addProduct(new Product("bread", 0.80));
        basket.addProduct(new Product("milk", 1.30));
        return basket.getProducts();
    }
}
