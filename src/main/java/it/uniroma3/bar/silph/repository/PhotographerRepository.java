package it.uniroma3.bar.silph.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.bar.silph.model.Photographer;

public interface PhotographerRepository extends CrudRepository<Photographer, Long>{
	public Photographer findByFirstNameAndSecondName(String firstName, String secondName);
}
