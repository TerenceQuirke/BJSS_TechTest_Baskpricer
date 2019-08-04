package com.bjss.techtest.basketpricer.model;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;

public class Basket {
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

    public String calculateBasketSubtotal(){
        BigDecimal calculatedSubtotal = BigDecimal.ZERO;
        for (final Product product : products) {
            calculatedSubtotal = calculatedSubtotal.add(product.getPrice());
        }
        subtotal = calculatedSubtotal;
        return currencyFormatter(calculatedSubtotal);
    }

    public String calculateOffers(){
        Map<ProductType, Integer> frequencyMap = new HashMap<>();
        for (final Product product: products) {
            Integer count = frequencyMap.get(product.getName());
            if (count == null)
                count = 0;

            frequencyMap.put(product.getName(), count + 1);
        }
        if(frequencyMap.containsKey(ProductType.Apples)){
            if(frequencyMap.get(ProductType.Apples) >= 2)
            {
                BigDecimal appleDiscount = new BigDecimal(0);
                for (final Product product : products) {
                    if(product.getName() == ProductType.Apples)
                    {
                        BigDecimal discount = product.getPrice().movePointLeft(1);
                        appleDiscount = appleDiscount.add(discount.negate());
                    }
                }
                offerDiscount = offerDiscount.add(appleDiscount);
                if (!offersDetails.contains("Apples")){
                    offersDetails = offersDetails.replace("(No offers available)","");
                    offersDetails += "Apples 10% off: " + currencyFormatter(appleDiscount);
                }

            }
        }

        if(frequencyMap.containsKey(ProductType.Bread) & frequencyMap.containsKey(ProductType.Soup)){
            if(frequencyMap.get(ProductType.Soup) >= 2 && frequencyMap.get(ProductType.Bread) >= 1){
                long soupCount = frequencyMap.get(ProductType.Soup);
                long breadCount = frequencyMap.get(ProductType.Bread);
                BigDecimal breadCost = new BigDecimal(0);
                BigDecimal breadDiscount = new BigDecimal(0);
                for (final Product product : products) {
                    if(product.getName() == ProductType.Bread)
                    {
                        breadCost = product.getPrice();
                    }
                }
                if(breadCount >= (soupCount/2)){
                    breadDiscount = breadDiscount.add(new BigDecimal((breadCost.doubleValue()/2) * (soupCount/2)* -1));
                }
                else
                {
                    breadDiscount = breadDiscount.add(new BigDecimal((breadCost.doubleValue()/2) * (breadCount)* -1));
                }
                if (!offersDetails.contains("Bread")){
                    offersDetails = offersDetails.replace("(No offers available)","");
                    offersDetails += " Bread 50% off when buying 2 Soups: " + currencyFormatter(breadDiscount);
                }
                offerDiscount = offerDiscount.add(breadDiscount);
            }
        }

        return currencyFormatter(offerDiscount);
    }

    public String getOffersDetails(){
        if(offersDetails.contains("(No offers available)")){
            calculateOffers();
        }
        return offersDetails;
    }

    public String getTotal(){
        if(offersDetails.contains("(No offers available)")){
            calculateOffers();
        }
        return currencyFormatter(subtotal.add(offerDiscount));
    }

    private String currencyFormatter(BigDecimal amount){
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.UK);
        return format.format(amount);
    }
}
