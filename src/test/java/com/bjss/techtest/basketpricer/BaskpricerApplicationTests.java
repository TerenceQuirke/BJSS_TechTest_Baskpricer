package com.bjss.techtest.basketpricer;

import com.bjss.techtest.basketpricer.model.Basket;
import com.bjss.techtest.basketpricer.model.Product;
import com.bjss.techtest.basketpricer.model.ProductType;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import static org.apache.logging.log4j.util.Strings.EMPTY;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BaskPricerApplicationTests {

	@Test
	public void BasketCalculatesCorrectSubTotal()
	{
		Basket basket = new Basket();
		Product apples = new Product(ProductType.Apples, BigDecimal.valueOf(1.5));
		Product milk = new Product(ProductType.Milk, BigDecimal.valueOf(1.5));
		Product soup = new Product(ProductType.Soup, BigDecimal.valueOf(1.5));
		Product bread = new Product(ProductType.Bread, BigDecimal.valueOf(1.5));
		basket.addProduct(apples);
		basket.addProduct(milk);
		basket.addProduct(soup);
		basket.addProduct(bread);
		String expectedSubtotal = currencyFormatter(apples.getPrice().add(milk.getPrice()).add(soup.getPrice()).add(bread.getPrice()));
		String actualSubTotal = basket.calculateBasketSubtotal();
		Assert.assertTrue(expectedSubtotal.equals(actualSubTotal));
	}

	@Test
	public void BasketCalculateCorrectOffersOnApples(){
		Basket basket = new Basket();
		Product apples = new Product(ProductType.Apples, BigDecimal.valueOf(1));
		basket.addProduct(apples);
		basket.addProduct(apples);
		String expectedOffers= currencyFormatter(BigDecimal.valueOf((((apples.getPrice().doubleValue() * 2) / 100) * 10) * -1));
		String actualOffers = basket.calculateOffers();
		String expectedOfferMessage = "Apples 10% off: " + expectedOffers;
		String actualOfferMessage = basket.getOffersDetails();
		Assert.assertTrue("Expected: " + expectedOffers + ", Actual: " + actualOffers,expectedOffers.equals(actualOffers));
		Assert.assertTrue("Expected: " + expectedOfferMessage + ", Actual: " + basket.getOffersDetails(),actualOfferMessage.equals(expectedOfferMessage));
	}

	@Test
	public void BasketCalculateCorrectOffersOnSoupAndBread(){
		Basket basket = new Basket();
		Product bread = new Product(ProductType.Bread, BigDecimal.valueOf(0.8));
		Product soup = new Product(ProductType.Soup, BigDecimal.valueOf(0.65));
		basket.addProduct(soup);
		basket.addProduct(soup);
		basket.addProduct(bread);
		String expectedOffers= currencyFormatter(BigDecimal.valueOf(bread.getPrice().doubleValue()/2 * -1));
		String actualOffers = basket.calculateOffers();
		String expectedOfferMessage = " Bread 50% off when buying 2 Soups: " + expectedOffers;
		String actualOfferMessage = basket.getOffersDetails();
		Assert.assertTrue("Expected: " + expectedOffers + ", Actual: " + actualOffers,expectedOffers.equals(actualOffers));
		Assert.assertTrue("Expected: " + expectedOfferMessage + ", Actual: " + basket.getOffersDetails(),actualOfferMessage.equals(expectedOfferMessage));
	}

	@Test
	public void BasketCalculateCorrectOffersOnEverything(){
		Basket basket = new Basket();
		Product bread = new Product(ProductType.Bread, BigDecimal.valueOf(1));
		Product soup = new Product(ProductType.Soup, BigDecimal.valueOf(1));
		Product apples = new Product(ProductType.Apples, BigDecimal.valueOf(1));
		basket.addProduct(soup);
		basket.addProduct(soup);
		basket.addProduct(bread);
		basket.addProduct(apples);
		basket.addProduct(apples);
		BigDecimal expectedAppleOffersValues = BigDecimal.valueOf((((apples.getPrice().doubleValue() * 2) / 100) * 10) * -1);
		BigDecimal expectedBreadOffersValues = BigDecimal.valueOf(bread.getPrice().doubleValue()/2 * -1);
		String expectedAppleOffers = currencyFormatter(expectedAppleOffersValues);
		String expectedBreadOffers = currencyFormatter(expectedBreadOffersValues);
		String expectedOffers = currencyFormatter(expectedAppleOffersValues.add(expectedBreadOffersValues));
		String actualOffers = basket.calculateOffers();
		String expectedOfferMessage ="Apples 10% off: " + expectedAppleOffers + " Bread 50% off when buying 2 Soups: " + expectedBreadOffers;
		String actualOfferMessage = basket.getOffersDetails();
		Assert.assertTrue("Expected: " + expectedOffers + ", Actual: " + actualOffers,expectedOffers.equals(actualOffers));
		Assert.assertTrue("Expected: " + expectedOfferMessage + ", Actual: " + basket.getOffersDetails(),actualOfferMessage.equals(expectedOfferMessage));
	}

	private String currencyFormatter(BigDecimal amount){
		NumberFormat format = NumberFormat.getCurrencyInstance(Locale.UK);
		return format.format(amount);
	}

}
