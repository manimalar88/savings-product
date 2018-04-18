package com.ing.product;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ing.product.app.ProductApp;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProductApp.class)
public class IngProductApplicationTests {

	@Test
	public void contextLoads() {
	}

}
