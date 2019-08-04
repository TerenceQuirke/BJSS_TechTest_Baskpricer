package com.bjss.techtest.basketpricer.controller;

import com.bjss.techtest.basketpricer.model.ProductType;
import com.bjss.techtest.basketpricer.service.BasketCalculatorResponse;
import com.bjss.techtest.basketpricer.service.CalculateBasketPriceRequest;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
/**
 * the one and only Controller for the PriceBasket service
 *
 * @author Terence Quirke
 *
 */
@RestController
@RequestMapping("/api/v1/")
public class PriceBasketController {
    /**
     * Create CalculateBasketPriceRequest basketProducts.
     *
     * @param basketProducts the CalculateBasketPriceRequest
     * @return response the BasketCalculatorResponse
     */
    @RequestMapping(method = RequestMethod.POST, value="/calculateBasketPrice")
    @ResponseBody
    public BasketCalculatorResponse calculateBasketPrice(@RequestBody CalculateBasketPriceRequest basketProducts){
        List<ProductType> productTypeList = new LinkedList<ProductType>();
        for (final String basketProduct: basketProducts.getPriceBasket()) {
            ProductType productType = searchEnum(ProductType.class, basketProduct);
            if(productType != null){
                productTypeList.add(productType);
            }
        }
        BasketCalculatorResponse response = new BasketCalculatorResponse(productTypeList);
        return response;
    }

    /**
     * Searches through provided Enum for the Enum value based on the string provided
     *
     * @param enumeration the Enumeration Class
     * @param search the Enumeration Class
     * @return the Matching enum or if its not found return null
     */
    private static <T extends Enum<?>> T searchEnum(Class<T> enumeration,
                                                   String search) {
        for (T each : enumeration.getEnumConstants()) {
            if (each.name().compareToIgnoreCase(search) == 0) {
                return each;
            }
        }
        return null;
    }
}
