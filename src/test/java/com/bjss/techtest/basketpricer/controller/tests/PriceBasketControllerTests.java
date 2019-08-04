package com.bjss.techtest.basketpricer.controller.tests;

import com.bjss.techtest.basketpricer.controller.PriceBasketController;
import com.bjss.techtest.basketpricer.model.Product;
import com.bjss.techtest.basketpricer.model.ProductType;
import com.bjss.techtest.basketpricer.service.BasketCalculatorResponse;
import com.bjss.techtest.basketpricer.service.CalculateBasketPriceRequest;
import com.bjss.techtest.basketpricer.service.CurrencyHandler;
import com.bjss.techtest.basketpricer.service.ProductPriceLookup;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PriceBasketControllerTests {
    private PriceBasketController priceBasketController;
    private CalculateBasketPriceRequest calculateBasketPriceRequest;
    private BasketCalculatorResponse basketCalculatorResponse;
    private ProductPriceLookup productPriceLookup;

    private CurrencyHandler currencyHandler;

    private Product appleProduct;
    private Product milkProduct;
    private Product breadProduct;
    private Product soupProduct;

    @Before
    public void setup(){
        priceBasketController = new PriceBasketController();
        calculateBasketPriceRequest = new CalculateBasketPriceRequest();
        productPriceLookup = new ProductPriceLookup();

        currencyHandler = new CurrencyHandler();

        appleProduct = productPriceLookup.ProductWithPrice(ProductType.Apples);
        milkProduct = productPriceLookup.ProductWithPrice(ProductType.Milk);
        breadProduct = productPriceLookup.ProductWithPrice(ProductType.Bread);
        soupProduct = productPriceLookup.ProductWithPrice(ProductType.Soup);
    }

    @Test
    public void AppleSpecialTestResponseCorrect(){
        String[] TestData = "Apples,Apples".split(",");
        calculateBasketPriceRequest.setPriceBasket(TestData);

        basketCalculatorResponse = priceBasketController.calculateBasketPrice(calculateBasketPriceRequest);

        String actualSubtotal = basketCalculatorResponse.getSubtotal();
        String actualOfferDetails = basketCalculatorResponse.getOffers();
        String actualTotal = basketCalculatorResponse.getTotal();

        String expectedSubtotal = currencyHandler.currencyFormatter(appleProduct.getPrice().multiply(new BigDecimal(2)));
        BigDecimal ExpectedDiscount = appleProduct.getPrice().multiply(new BigDecimal(2)).multiply(new BigDecimal(0.1));
        String expectedOfferDetails = "Apples 10% off: " + currencyHandler.currencyFormatter(ExpectedDiscount.negate());
        String expectedTotal = currencyHandler.currencyFormatter(appleProduct.getPrice().multiply(new BigDecimal(2)).subtract(ExpectedDiscount));

        Assert.assertTrue("Expected Subtotal: " + expectedSubtotal + ", Actual SubTotal: " + actualSubtotal ,
                actualSubtotal.equals(expectedSubtotal));
        Assert.assertTrue("Expected OfferDetails: " + expectedOfferDetails + ", Actual OfferDetails: " + actualOfferDetails ,
                actualOfferDetails.equals(expectedOfferDetails));
        Assert.assertTrue("Expected Total: " + expectedTotal + ", Actual Total: " + actualTotal ,
                actualTotal.equals(expectedTotal));
    }

    @Test
    public void BreadAndSoupSpecialTest(){
        String[] TestData = "Bread,Soup,Soup".split(",");
        calculateBasketPriceRequest.setPriceBasket(TestData);

        basketCalculatorResponse = priceBasketController.calculateBasketPrice(calculateBasketPriceRequest);

        String actualSubtotal = basketCalculatorResponse.getSubtotal();
        String actualOfferDetails = basketCalculatorResponse.getOffers();
        String actualTotal = basketCalculatorResponse.getTotal();

        String expectedSubtotal = currencyHandler.currencyFormatter(soupProduct.getPrice().multiply(new BigDecimal(2)).add(breadProduct.getPrice()));
        BigDecimal ExpectedDiscount = breadProduct.getPrice().divide(new BigDecimal(2));
        String expectedOfferDetails = "Bread 50% off when buying 2 Soups: " + currencyHandler.currencyFormatter(ExpectedDiscount.negate());
        String expectedTotal = currencyHandler.currencyFormatter(soupProduct.getPrice().multiply(new BigDecimal(2)).add(breadProduct.getPrice()).subtract(ExpectedDiscount));

        Assert.assertTrue("Expected Subtotal: " + expectedSubtotal + ", Actual SubTotal: " + actualSubtotal ,
                actualSubtotal.equals(expectedSubtotal));
        Assert.assertTrue("Expected OfferDetails: " + expectedOfferDetails + ", Actual OfferDetails: " + actualOfferDetails ,
                actualOfferDetails.equals(expectedOfferDetails));
        Assert.assertTrue("Expected Total: " + expectedTotal + ", Actual Total: " + actualTotal ,
                actualTotal.equals(expectedTotal));
    }

    @Test
    public void AllSpecialsTest(){
        String[] TestData = "Bread,Soup,Soup,Apples,Apples".split(",");
        calculateBasketPriceRequest.setPriceBasket(TestData);

        basketCalculatorResponse = priceBasketController.calculateBasketPrice(calculateBasketPriceRequest);

        String actualSubtotal = basketCalculatorResponse.getSubtotal();
        String actualOfferDetails = basketCalculatorResponse.getOffers();
        String actualTotal = basketCalculatorResponse.getTotal();


        String expectedSubtotal = currencyHandler.currencyFormatter(
                currencyHandler.sumBigDecimals(new BigDecimal[]{
                        breadProduct.getPrice(),
                        soupProduct.getPrice(),
                        soupProduct.getPrice(),
                        appleProduct.getPrice(),
                        appleProduct.getPrice()
                }));
        BigDecimal expectedBreadDiscount = breadProduct.getPrice().divide(new BigDecimal(2));
        BigDecimal expectedAppleDiscount = appleProduct.getPrice().multiply(new BigDecimal(2)).multiply(new BigDecimal(0.1));
        BigDecimal expectedDiscount = expectedBreadDiscount.add(expectedAppleDiscount);
        String expectedOfferDetails = "Apples 10% off: " + currencyHandler.currencyFormatter(expectedAppleDiscount.negate())
                + " Bread 50% off when buying 2 Soups: "+ currencyHandler.currencyFormatter(expectedBreadDiscount.negate());
        String expectedTotal = currencyHandler.currencyFormatter((currencyHandler.sumBigDecimals(new BigDecimal[]{
                breadProduct.getPrice(),
                soupProduct.getPrice(),
                soupProduct.getPrice(),
                appleProduct.getPrice(),
                appleProduct.getPrice()
        })).subtract(expectedDiscount));

        Assert.assertTrue("Expected Subtotal: " + expectedSubtotal + ", Actual SubTotal: " + actualSubtotal ,
                actualSubtotal.equals(expectedSubtotal));
        Assert.assertTrue("Expected OfferDetails: " + expectedOfferDetails + ", Actual OfferDetails: " + actualOfferDetails ,
                actualOfferDetails.equals(expectedOfferDetails));
        Assert.assertTrue("Expected Total: " + expectedTotal + ", Actual Total: " + actualTotal ,
                actualTotal.equals(expectedTotal));
    }
}
