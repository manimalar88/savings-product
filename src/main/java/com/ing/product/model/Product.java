package com.ing.product.model;

public class Product {
	
	private String name;
	
	private ProductType type;
	
	public Product(String name, ProductType type) {
		super();
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ProductType getType() {
		return type;
	}

	public void setType(ProductType type) {
		this.type = type;
	}
	
	
}
