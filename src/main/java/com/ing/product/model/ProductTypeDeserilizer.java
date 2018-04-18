package com.ing.product.model;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class ProductTypeDeserilizer extends JsonDeserializer<ProductType> {
	  @Override
	  public ProductType deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
	    ObjectCodec oc = jp.getCodec();
	    JsonNode node = oc.readTree(jp);
	    final String name = node.get("productType").asText();
	    return ProductType.valueOf(name);
	  }
	   
	   
}