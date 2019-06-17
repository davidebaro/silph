package it.uniroma3.bar.silph.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.bar.silph.model.Customer;
import it.uniroma3.bar.silph.model.Request;
import it.uniroma3.bar.silph.repository.CustomerRepository;

@Service
public class CustomerService {
	@Autowired
	private CustomerRepository customerRepository;
	
	@Transactional
	public Customer add(Customer customer) {
		return customerRepository.save(customer);
	}
	
}
