package it.uniroma3.bar.silph.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.bar.silph.model.User;

public interface UserRepository extends CrudRepository<User, String>{

}
