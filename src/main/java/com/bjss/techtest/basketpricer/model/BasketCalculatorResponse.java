package com.bjss.techtest.basketpricer.model;

import com.bjss.techtest.basketpricer.model.Basket;
import com.bjss.techtest.basketpricer.model.ProductType;

import java.util.List;

public class BasketCalculatorResponse {
    private final String subtotal;
    private final String offers;
    private final String total;

    public BasketCalculatorResponse(List<ProductType> basketProducts){
        Basket basket = new Basket(basketProducts);

        this.subtotal = basket.getFormattedBasketSubtotal();
        this.offers = basket.getOffersDetails();
        this.total = basket.getTotal();
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
