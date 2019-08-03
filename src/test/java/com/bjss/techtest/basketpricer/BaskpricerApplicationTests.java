package com.bjss.techtest.basketpricer;

import com.bjss.techtest.basketpricer.model.Basket;
import com.bjss.techtest.basketpricer.model.Product;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BaskpricerApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public void BasketCalculatesCorrectPrice()
	{
		Basket basket = new Basket();
		Product apples = new Product("apples", 1.5);
		Product milk = new Product("milk", 1.5);
		Product soup = new Product("soup", 1.5);
		Product bread = new Product("bread", 1.5);
		basket.addProduct(apples);
		basket.addProduct(milk);
		basket.addProduct(soup);
		basket.addProduct(bread);
		double expectedSubtotal = apples.getPrice() + milk.getPrice() + soup.getPrice() + bread.getPrice();
		double actualSubTotal = basket.calculateBasketSubtotal();
		Assert.assertTrue(expectedSubtotal == actualSubTotal);
	}

}
