package com.ing.product.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.ing.product.model.Customer;
import com.ing.product.model.Product;
import com.ing.product.model.ProductType;

public class DataUtil {

	public final static List<Product> PRODUCT_LIST = Arrays.asList(new Product("Savings-silver",ProductType.SAVINGS),new Product("Current-silver",ProductType.CURRENT),
			new Product("Savings-Gold",ProductType.SAVINGS),new Product("Current-Gold",ProductType.CURRENT));
	
	public final static List<Customer> CUSTOMER_LIST = Arrays.asList(new Customer("ING-11111","Alex","alex@ing.com"),new Customer("ING-22222","Charlie","charlie@ing.com"),
			new Customer("ING-33333","jacky","jacky@ing.com"));
	
	
	public final static Map<String, List<Product>> customerProductsMap = new HashMap<String, List<Product>>() {
		{
			put("ING-11111", Arrays.asList(new Product("Current-Gold",ProductType.CURRENT)));
		}
		
	};
	
	
}

