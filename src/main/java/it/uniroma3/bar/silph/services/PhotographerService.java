package it.uniroma3.bar.silph.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.bar.silph.model.Photographer;
import it.uniroma3.bar.silph.repository.PhotographerRepository;

@Service
public class PhotographerService {
	
	@Autowired
	private PhotographerRepository photographerRepository;
	
	@Transactional
	public Photographer get(Photographer photographer) {
		return photographerRepository.findById(photographer.getId()).get();
	}
	
	@Transactional
	public Photographer get(Long id) {
		return photographerRepository.findById(id).get();
	}
	
	@Transactional
	public List<Photographer> getAll(){
		return (List<Photographer>) photographerRepository.findAll();
	}
	
	@Transactional
	public boolean contains(Photographer photographer) {
		Photographer realPhotographer = photographerRepository.findByFirstNameAndSecondName(photographer.getFirstName(), photographer.getSecondName());
		return realPhotographer!=null;
	}
	
	@Transactional
	public Photographer addPhotographer(Photographer photographer) {
		return photographerRepository.save(photographer);
	}
}
