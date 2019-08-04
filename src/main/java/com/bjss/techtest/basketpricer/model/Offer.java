package com.bjss.techtest.basketpricer.model;

import com.bjss.techtest.basketpricer.service.CurrencyHandler;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

import static com.bjss.techtest.basketpricer.model.ProductType.Apples;

public class Offer {
    private final Basket basket;
    private final CurrencyHandler currencyConverter;

    public Offer(Basket basket) {
        this.basket = basket;
        this.currencyConverter = new CurrencyHandler();
    }


    public String calculateOffers() {
        Map<ProductType, Integer> frequencyMap = getProductTypeIntegerMap();
        if (frequencyMap.containsKey(Apples)) {
            if (frequencyMap.get(Apples) >= 2) {
                BigDecimal appleDiscount = new BigDecimal(0);
                for (final Product product : basket.getProducts()) {
                    if (product.getName() == Apples) {
                        BigDecimal discount = product.getPrice().movePointLeft(1);
                        appleDiscount = appleDiscount.add(discount.negate());
                    }
                }
                basket.setOfferDiscount(basket.getOfferDiscount().add(appleDiscount));
                if (!basket.getOffersDetails().contains("Apples")) {
                    basket.setOffersDetails(basket.getOffersDetails().replace("(No offers available)", ""));
                    basket.setOffersDetails(basket.getOffersDetails() + "Apples 10% off: " + currencyConverter.currencyFormatter(appleDiscount));
                }
            }
        }

        if (frequencyMap.containsKey(ProductType.Bread) & frequencyMap.containsKey(ProductType.Soup)) {
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
                    breadDiscount = breadDiscount.add(new BigDecimal((breadCost.doubleValue() / 2) * (soupCount / 2) * -1));
                } else {
                    breadDiscount = breadDiscount.add(new BigDecimal((breadCost.doubleValue() / 2) * (breadCount) * -1));
                }
                if (!basket.getOffersDetails().contains("Bread")) {
                    basket.setOffersDetails(basket.getOffersDetails().replace("(No offers available)", ""));
                    basket.setOffersDetails(basket.getOffersDetails() + " Bread 50% off when buying 2 Soups: " + currencyConverter.currencyFormatter(breadDiscount));
                }
                basket.setOfferDiscount(basket.getOfferDiscount().add(breadDiscount));
            }
        }

        return currencyConverter.currencyFormatter(basket.getOfferDiscount());
    }

    private Map<ProductType, Integer> getProductTypeIntegerMap() {
        Map<ProductType, Integer> frequencyMap = new HashMap<ProductType, Integer>();
        for (final Product product : basket.getProducts()) {
            Integer count = frequencyMap.get(product.getName());
            if (count == null)
                count = 0;

            frequencyMap.put(product.getName(), count + 1);
        }
        return frequencyMap;
    }
}