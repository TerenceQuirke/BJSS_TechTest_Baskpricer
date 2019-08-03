package com.bjss.techtest.basketpricer.service;

import com.bjss.techtest.basketpricer.model.Basket;

import java.text.NumberFormat;
import java.util.Locale;

public class BasketCalculatorResponse {
    private final Basket basket;
    private final String subtotal;
    private final String offers;
    private final String total;

    public BasketCalculatorResponse(Basket basket){
        this.basket = basket;
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.UK);
        this.subtotal = format.format(basket.calculateBasketSubtotal());
        this.offers = "(No offers available)";
        this.total = this.subtotal;
    }

    public String getSubtotal(){
        return subtotal;
    }

    public String getOffers(){
        return offers;
    }

    public String getTotal(){
        return total;
    }
}
