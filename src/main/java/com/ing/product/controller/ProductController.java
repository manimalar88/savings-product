package com.ing.product.controller;

import java.util.List;
import java.util.Optional;
import java.util.Map.Entry;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ing.product.model.Product;
import com.ing.product.model.ProductType;
import com.ing.product.utils.DataUtil;

@RestController
public class ProductController {

	private static final String COUNTRY_CODE = "NL";

	private static final String CHECK_DIGITS = "91";
	
	private static final String BANK_ID = "BING";

	@RequestMapping(value = "products/{customerId}", method = RequestMethod.GET)
	public List<Product> getProduct(@PathVariable String customerId) {
		Optional<Entry<String, List<Product>>> products = DataUtil.customerProductsMap.entrySet().stream().filter(customer -> customer.getKey().equals(customerId)).findFirst();
		if(products.isPresent()) {
			return products.get().getValue();
		}else {
			return null;
		}				
		
	}
	
	@RequestMapping(value = "products/{customerId}/{productType}", method = RequestMethod.GET)
	public Product getProductByType(@PathVariable String customerId, @PathVariable ProductType productType) {
		Entry<String, List<Product>> products = DataUtil.customerProductsMap.entrySet().stream().filter(customer -> customer.getKey().equals(customerId)).findFirst().get();		
		return products.getValue().stream().filter(p->p.getType().equals(productType)).findFirst().orElse(null);			
	}
	
	@RequestMapping(value = "products/iban", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String generateIBAN(@RequestBody ProductType productType) {
		long number = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
		return COUNTRY_CODE + CHECK_DIGITS + BANK_ID + String.valueOf(number);					
	}
}
