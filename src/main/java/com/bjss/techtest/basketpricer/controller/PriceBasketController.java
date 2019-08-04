package com.bjss.techtest.basketpricer.controller;

import com.bjss.techtest.basketpricer.model.Basket;
import com.bjss.techtest.basketpricer.model.Product;
import com.bjss.techtest.basketpricer.model.ProductType;
import com.bjss.techtest.basketpricer.service.BasketCalculatorResponse;
import com.bjss.techtest.basketpricer.service.CalculateBasketPriceRequest;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class PriceBasketController {
    @RequestMapping(method = RequestMethod.POST, value="/calculateBasketPrice")
    @ResponseBody
    public BasketCalculatorResponse calculateBasketPrice(@RequestBody CalculateBasketPriceRequest basketProducts){
        List<ProductType> productTypeList = new LinkedList<ProductType>();
        for (final String basketProduct: basketProducts.getProductList()) {
            productTypeList.add(searchEnum(ProductType.class, basketProduct));
        }
        BasketCalculatorResponse response = new BasketCalculatorResponse(productTypeList);
        return response;
    }

    public static <T extends Enum<?>> T searchEnum(Class<T> enumeration,
                                                   String search) {
        for (T each : enumeration.getEnumConstants()) {
            if (each.name().compareToIgnoreCase(search) == 0) {
                return each;
            }
        }
        return null;
    }
}
