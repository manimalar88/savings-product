package com.ing.product.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


@Entity
@Table(name = "T_CUSTOMER")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class , property = "id") 
public class Customer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	private String email;
	
	@ManyToMany(cascade = { 
	    CascadeType.PERSIST, 
	    CascadeType.MERGE
	})
	@JoinTable(name = "t_customer_product",
	    joinColumns = @JoinColumn(name = "customer_id"),
	    inverseJoinColumns = @JoinColumn(name = "product_id")
	)	
	private Set<Product> products = new HashSet<>();

	public Customer(Long id, String name, String email) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
	}

	public Customer() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}
	
	
    public void addProduct(Product product) {
    	products.add(product);
    	product.getCustomers().add(this);
    }
 
    public void removeTag(Product product) {
    	products.remove(product);
    	product.getCustomers().remove(this);
    }
	
	
	
}
