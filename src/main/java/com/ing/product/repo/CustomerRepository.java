package com.ing.product.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ing.product.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{

}