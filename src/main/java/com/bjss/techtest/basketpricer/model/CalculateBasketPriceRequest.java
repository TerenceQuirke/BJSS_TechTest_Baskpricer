package com.bjss.techtest.basketpricer.model;

import com.fasterxml.jackson.annotation.*;



public class CalculateBasketPriceRequest {
    private String[] PriceBasket;

    @JsonProperty("PriceBasket")
    public String[] getPriceBasket() { return PriceBasket; }
    @JsonProperty("PriceBasket")
    public void setPriceBasket(String[] value) { this.PriceBasket = value; }
}
