package com.bjss.techtest.basketpricer.model;

import com.bjss.techtest.basketpricer.service.CurrencyHandler;
import com.bjss.techtest.basketpricer.service.ProductPriceLookup;

import java.math.BigDecimal;
import java.util.*;

public class Basket {
    private final Offer offer = new Offer(this);
    private final CurrencyHandler currencyHandler = new CurrencyHandler();
    private List<Product> products = new LinkedList<Product>();
    private BigDecimal offerDiscount = new BigDecimal(0);
    private String offersDetails = "(No offers available)";
    private BigDecimal subtotal;
    private ProductPriceLookup productPriceLookup;

    public Basket(){
        productPriceLookup= new ProductPriceLookup();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void addProduct(ProductType productType)
    {
        products.add(productPriceLookup.ProductWithPrice(productType));
    }

    public List<Product> getProducts()
    {
        return products;
    }

    public BigDecimal getOfferDiscount(){
        return offerDiscount;
    }

    public String calculateBasketSubtotal(){
        BigDecimal calculatedSubtotal = BigDecimal.ZERO;
        for (final Product product : products) {
            calculatedSubtotal = calculatedSubtotal.add(product.getPrice());
        }
        subtotal = calculatedSubtotal;
        return currencyHandler.currencyFormatter(calculatedSubtotal);
    }

    public String calculateOffers(){

        return offer.calculateOffers();
    }

    public String getOffersDetails(){
        if(offersDetails.contains("(No offers available)")){
            offer.calculateOffers();
        }
        return offersDetails;
    }

    public String getTotal(){
        if(offersDetails.contains("(No offers available)")){
            offer.calculateOffers();
        }
        return currencyHandler.currencyFormatter(subtotal.add(offerDiscount));
    }

    public void setOffersDetails(String offerDetails){
        this.offersDetails = offerDetails;
    }

    private String currencyFormatter(BigDecimal amount){
        return currencyHandler.currencyFormatter(amount);
    }

    public void setOfferDiscount(BigDecimal value) {
        this.offerDiscount = value;
    }
}
