package com.ing.product.controller;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ing.product.app.MessageSender;
import com.ing.product.model.Customer;
import com.ing.product.model.Product;
import com.ing.product.model.ProductType;
import com.ing.product.repo.CustomerRepository;
import com.ing.product.repo.ProductRepository;

@RestController
@RequestMapping("/products")
//@Api(value = "products")
public class ProductController {

	private static final String COUNTRY_CODE = "NL";

	private static final String CHECK_DIGITS = "91";

	private static final String BANK_ID = "BING";

	@Autowired
	ProductRepository productRepo;

	@Autowired
	CustomerRepository customerRepo;

	@Autowired
	MessageSender messageSender;
	
	

	public ProductController(ProductRepository productRepo, CustomerRepository customerRepo,
			MessageSender messageSender) {
		super();
		this.productRepo = productRepo;
		this.customerRepo = customerRepo;
		this.messageSender = messageSender;
	}

	@GetMapping("/{customerId}")
	public Set<Product> getProduct(@PathVariable Long customerId) {

		Optional<Customer> customer = customerRepo.findById(customerId);
		if (customer.isPresent()) {
			return customer.get().getProducts();
		} else {
			return null;
		}

	}

	@GetMapping
	public List<Product> getProducts() {
		return productRepo.findAll();
	}

	@GetMapping("/{customerId}/{productType}")
	public Product getProductByType(@PathVariable Long customerId, @PathVariable ProductType productType) {

		Optional<Customer> customer = customerRepo.findById(customerId);

		if (customer.isPresent()) {
			if (!customer.get().getProducts().isEmpty()) {
				Optional<Product> product = customer.get().getProducts().stream()
						.filter(p -> p.getType().equals(productType)).findFirst();
				if (product.isPresent()) {
					return product.get();
				}
			}
		}
		return null;

	}

	@GetMapping("/iban")
	public String generateIBAN() throws Exception {
		long number = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
		return COUNTRY_CODE + CHECK_DIGITS + BANK_ID + String.valueOf(number);
	}
	
	@PostMapping("/{customerId}")
	public ResponseEntity<?> addProduct(@PathVariable Long customerId, Product product) throws Exception{
		Optional<Customer> customer = customerRepo.findById(customerId);
		if(!customer.isPresent()) {
			throw new Exception("No customer found");
		}
		Set<Product> products = customer.get().getProducts();		
		if( findByType(products,ProductType.SAVINGS) == null){
			
			if(findByType(products,ProductType.CURRENT) == null) {
				throw new Exception("Current product is not exist");
			}else {
				validateCompliance(customerId);
				customer.get().addProduct(product);
				customerRepo.save(customer.get());
			}
			
		}else {
			throw new Exception("Savings product already exists");
		}
		
		return ResponseEntity.ok().build();
	}
	
	private void validateCompliance(Long customerId) throws Exception {
		if(!messageSender.sendMessage(customerId)) {
			throw new Exception("Customer does not meet compliance");
		}
	}
	
	private Product findByType(Collection<Product> list, ProductType type) {
	    return list.stream().filter(p -> p.getType().equals(type)).findFirst().orElse(null);
	}
	
	
}
