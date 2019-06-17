package it.uniroma3.bar.silph.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.bar.silph.model.Photo;
import it.uniroma3.bar.silph.model.Request;

public interface RequestRepository extends CrudRepository<Request, Long>{
	public List<Request> findByPhotos(List<Photo> photos);
}
