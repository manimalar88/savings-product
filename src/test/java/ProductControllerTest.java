import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.ing.product.controller.ProductController;
import com.ing.product.model.Customer;
import com.ing.product.model.Product;
import com.ing.product.model.ProductType;
import com.ing.product.repo.CustomerRepository;
import com.ing.product.repo.ProductRepository;

public class ProductControllerTest {
	
	Customer existingCurrent;
	
	Customer nonExistingProducts;
	
	Customer existingSavings;
	
	Product productCurrent;
	
	Product productSavings;
	
	List<Product> existingProducts = new ArrayList<>();
	
	@Before
	public void setUp() {
		existingCurrent = new Customer(1l,"Test 1","test@hcl.com");
		nonExistingProducts = new Customer(1l,"Test 1","test@hcl.com");
		productCurrent = new Product("Current-Test",ProductType.CURRENT);
		existingCurrent.addProduct(productCurrent);
		
		existingProducts.add(productCurrent);
		
		existingSavings = new Customer(1l,"Test 1","test@hcl.com");
		productSavings = new Product("Savings-Test",ProductType.SAVINGS);
		existingSavings.addProduct(productSavings);
		
	}
	
	@Test
	public void lookupProductsById() {
		CustomerRepository customerRepo = mock(CustomerRepository.class);
		when(customerRepo.findById(anyLong())).thenReturn(Optional.of(existingCurrent));		
		ProductController testController = new ProductController(null, customerRepo, null);
		
		Set<Product> product = testController.getProduct(1l);
		
		assertThat(product).isNotEmpty();
		
	}
	
	@Test
	public void lookupProductsByInvalidCustomerId() {
		CustomerRepository customerRepo = mock(CustomerRepository.class);
		when(customerRepo.findById(anyLong())).thenReturn(Optional.empty());		
		ProductController testController = new ProductController(null, customerRepo, null);
		
		Set<Product> product = testController.getProduct(1l);
		
		assertThat(product).isNull();
		
	}
	
	@Test
	public void lookupEmptyProducts() {
		CustomerRepository customerRepo = mock(CustomerRepository.class);
		when(customerRepo.findById(anyLong())).thenReturn(Optional.of(nonExistingProducts));		
		ProductController testController = new ProductController(null, customerRepo, null);
		
		Set<Product> product = testController.getProduct(1l);
		
		assertThat(product).isEmpty();
		
	}
	
	@Test
	public void lookupProducts() {
		ProductRepository prodRepo = mock(ProductRepository.class);
		when(prodRepo.findAll()).thenReturn(existingProducts);		
		ProductController testController = new ProductController(prodRepo, null, null);
		
		List<Product> product = testController.getProducts();
		
		assertThat(product).isNotEmpty();
		
	}
	
	@Test
	public void lookupProductByType() {
		CustomerRepository customerRepo = mock(CustomerRepository.class);
		when(customerRepo.findById(anyLong())).thenReturn(Optional.of(existingCurrent));		
		ProductController testController = new ProductController(null, customerRepo, null);
		
		Product product = testController.getProductByType(1l, ProductType.CURRENT);
		
		assertThat(product).isNotNull();
		
	}
	
	@Test
	public void lookupProductByTypeNonExisting() {
		CustomerRepository customerRepo = mock(CustomerRepository.class);
		when(customerRepo.findById(anyLong())).thenReturn(Optional.of(existingCurrent));		
		ProductController testController = new ProductController(null, customerRepo, null);
		
		Product product = testController.getProductByType(1l, ProductType.SAVINGS);
		
		assertThat(product).isNull();
		
	}
	
	@Test
	public void testGenerateIBAN() throws Exception {
		ProductController testController = new ProductController(null, null, null);
		String iban = testController.generateIBAN();
		assertThat(iban).isNotNull();
	}
	
	@Test
	public void throwExceptionWhenNoCustomer() {
		CustomerRepository customerRepo = mock(CustomerRepository.class);
		when(customerRepo.findById(anyLong())).thenReturn(Optional.empty());		
		ProductController testController = new ProductController(null, customerRepo, null);
		
		try{
			testController.addProduct(12L, productSavings);
			fail("Exception should had been thrown");
		} catch(Exception e){
			assertThat(e.getMessage()).isEqualTo("No customer found");
		}
	}
	
	
	@Test
	public void throwExceptionWhenNoCurrrentProduct() {
		CustomerRepository customerRepo = mock(CustomerRepository.class);
		when(customerRepo.findById(anyLong())).thenReturn(Optional.of(nonExistingProducts));		
		ProductController testController = new ProductController(null, customerRepo, null);
		
		try{
			testController.addProduct(12L, productSavings);
			fail("Exception should had been thrown");
		} catch(Exception e){
			assertThat(e.getMessage()).isEqualTo("Current product is not exist");
		}
	}
	
	@Test
	public void throwExceptionWhenSavingsProductExists() {
		CustomerRepository customerRepo = mock(CustomerRepository.class);
		when(customerRepo.findById(anyLong())).thenReturn(Optional.of(existingSavings));		
		ProductController testController = new ProductController(null, customerRepo, null);
		
		try{
			testController.addProduct(12L, productSavings);
			fail("Exception should had been thrown");
		} catch(Exception e){
			assertThat(e.getMessage()).isEqualTo("Savings product already exists");
		}
	}

}
