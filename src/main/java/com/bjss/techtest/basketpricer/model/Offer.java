package com.bjss.techtest.basketpricer.model;

import com.bjss.techtest.basketpricer.service.CurrencyHandler;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bjss.techtest.basketpricer.model.ProductType.Apples;

public class Offer {
    private final Basket basket;

    private final CurrencyHandler currencyConverter;

    private BigDecimal offerDiscount;
    private BigDecimal appleDiscount = BigDecimal.ZERO;
    private BigDecimal soupAndBreadDiscount = BigDecimal.ZERO;
    private String offerDetails;

    private static String appleSpecial = "Apples 10% off: ";
    private static String soupAndBreadSpecial = "Bread 50% off when buying 2 Soups: ";

    private Map<ProductType, Integer> frequencyMap;

    public Offer(Basket basket) {
        this.basket = basket;
        this.currencyConverter = new CurrencyHandler();
        this.frequencyMap = getProductTypeIntegerMap(basket.getProducts());
        this.appleDiscount = calculateAppleDiscount();
        this.soupAndBreadDiscount = calculateBreadAndSoupDiscount();
        this.offerDiscount = appleDiscount.add(soupAndBreadDiscount);
        this.offerDetails = getOfferDetailsMessage();
    }

    public BigDecimal getOfferDiscount(){
        return offerDiscount;
    }

    public String getOfferDetailMessage()
    {
        return offerDetails;
    }

    private BigDecimal calculateAppleDiscount(){
        BigDecimal discountHolder = BigDecimal.ZERO;
        if (frequencyMap.containsKey(Apples)) {
            if (frequencyMap.get(Apples) >= 1) {
                BigDecimal appleDiscount = new BigDecimal(0);
                for (final Product product : basket.getProducts()) {
                    if (product.getName() == Apples) {
                        BigDecimal discount = product.getPrice().movePointLeft(1);
                        appleDiscount = appleDiscount.add(discount);
                    }
                }
                discountHolder = discountHolder.add(appleDiscount);
            }
        }
        return discountHolder;
    }

    private BigDecimal calculateBreadAndSoupDiscount() {
        BigDecimal discountHolder = BigDecimal.ZERO;
        if (frequencyMap.containsKey(ProductType.Bread) && frequencyMap.containsKey(ProductType.Soup)) {
            if (frequencyMap.get(ProductType.Soup) >= 2 && frequencyMap.get(ProductType.Bread) >= 1) {
                long soupCount = frequencyMap.get(ProductType.Soup);
                long breadCount = frequencyMap.get(ProductType.Bread);
                BigDecimal breadCost = new BigDecimal(0);
                BigDecimal breadDiscount = new BigDecimal(0);
                for (final Product product : basket.getProducts()) {
                    if (product.getName() == ProductType.Bread) {
                        breadCost = product.getPrice();
                    }
                }
                if (breadCount >= (soupCount / 2)) {
                    breadDiscount = breadDiscount.add(new BigDecimal((breadCost.doubleValue() / 2) * (soupCount / 2)));
                } else {
                    breadDiscount = breadDiscount.add(new BigDecimal((breadCost.doubleValue() / 2) * (breadCount)));
                }

                discountHolder = discountHolder.add(breadDiscount);
            }
        }

        return discountHolder;
    }

    private String getOfferDetailsMessage(){
        String proposedOfferMessage;
        if(soupAndBreadDiscount.equals(BigDecimal.ZERO) && appleDiscount.equals(BigDecimal.ZERO)){
            proposedOfferMessage = "(No offers available)";
        }
        else if(!soupAndBreadDiscount.equals(BigDecimal.ZERO) && appleDiscount.equals(BigDecimal.ZERO)){
            proposedOfferMessage = soupAndBreadSpecial + currencyConverter.currencyFormatter(soupAndBreadDiscount.negate()) ;
        }
        else if(soupAndBreadDiscount.equals(BigDecimal.ZERO) && !appleDiscount.equals(BigDecimal.ZERO)){
            proposedOfferMessage = appleSpecial + currencyConverter.currencyFormatter(appleDiscount.negate());
        }
        else{
            proposedOfferMessage = appleSpecial + currencyConverter.currencyFormatter(appleDiscount.negate()) + " " +
                    soupAndBreadSpecial + currencyConverter.currencyFormatter(soupAndBreadDiscount.negate());
        }
        return proposedOfferMessage;
    }

    private Map<ProductType, Integer> getProductTypeIntegerMap(List<Product> productList) {
        Map<ProductType, Integer> frequencyMap = new HashMap<ProductType, Integer>();
        for (final Product product : productList) {
            Integer count = frequencyMap.get(product.getName());
            if (count == null)
                count = 0;

            frequencyMap.put(product.getName(), count + 1);
        }
        return frequencyMap;
    }
}