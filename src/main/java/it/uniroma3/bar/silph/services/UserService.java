package it.uniroma3.bar.silph.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.bar.silph.model.User;
import it.uniroma3.bar.silph.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	public User getUser(String username) {
		return userRepository.findById(username).get();
	}
}
