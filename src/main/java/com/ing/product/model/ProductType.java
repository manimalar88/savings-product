package com.ing.product.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = ProductTypeDeserilizer.class)
public enum ProductType {

	SAVINGS, CURRENT;
	
}
