package com.bjss.techtest.basketpricer.model;

import com.bjss.techtest.basketpricer.service.CurrencyHandler;
import com.bjss.techtest.basketpricer.service.ProductPriceLookup;

import java.math.BigDecimal;
import java.util.*;

public class Basket {
    private Offer offer;
    private final CurrencyHandler currencyHandler = new CurrencyHandler();
    private List<Product> products = new LinkedList<Product>();
    private BigDecimal offerDiscount;
    private String offersDetails;
    private BigDecimal subtotal;
    private ProductPriceLookup productPriceLookup;


    public Basket(List<ProductType> productList){
        this.productPriceLookup= new ProductPriceLookup();
        for (final ProductType productType: productList) {
            addProduct(productType);
        }
        offer = new Offer(this);
        this.offerDiscount = offer.getOfferDiscount();
        this.offersDetails = offer.getOfferDetailMessage();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void addProduct(ProductType productType)
    {
        this.products.add(productPriceLookup.ProductWithPrice(productType));
    }

    public List<Product> getProducts()
    {
        return products;
    }

    public BigDecimal getOfferDiscount(){
        return offerDiscount;
    }

    public String getFormattedBasketSubtotal(){
        BigDecimal calculatedSubtotal = BigDecimal.ZERO;
        for (final Product product : products) {
            calculatedSubtotal = calculatedSubtotal.add(product.getPrice());
        }
        subtotal = calculatedSubtotal;
        return currencyHandler.currencyFormatter(calculatedSubtotal);
    }

    public String getOffersDetails(){
        return offersDetails;
    }

    public String getTotal(){
        return currencyHandler.currencyFormatter(subtotal.subtract(offerDiscount));
    }

    private void setOffersDetails(String offerDetails){
        this.offersDetails = offerDetails;
    }

    private String currencyFormatter(BigDecimal amount){
        return currencyHandler.currencyFormatter(amount);
    }

    public void setOfferDiscount(BigDecimal value) {
        this.offerDiscount = value;
    }
}
