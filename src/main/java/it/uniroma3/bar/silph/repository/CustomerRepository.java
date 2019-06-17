package it.uniroma3.bar.silph.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.bar.silph.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long>{
	
}
