package com.bjss.techtest.basketpricer.controller;

import com.bjss.techtest.basketpricer.model.Basket;
import com.bjss.techtest.basketpricer.model.Product;
import com.bjss.techtest.basketpricer.service.BasketCalculatorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class PriceBasketController {

    @RequestMapping("/basketcontents")
    public List<Product> checkBasketContents(){
        System.out.println("In submitBasket");
        Basket basket = new Basket();
        basket.addProduct(new Product("apples", 1));
        basket.addProduct(new Product("soup", 0.65));
        basket.addProduct(new Product("bread", 0.80));
        basket.addProduct(new Product("milk", 1.30));
        return basket.getProducts();
    }

    @RequestMapping(method = RequestMethod.POST, value="/calculateBasketPrice")
    @ResponseBody
    public BasketCalculatorResponse calculateBasketPrice(@RequestBody Basket basket){
        BasketCalculatorResponse response = new BasketCalculatorResponse(basket);
        return response;
    }
}
