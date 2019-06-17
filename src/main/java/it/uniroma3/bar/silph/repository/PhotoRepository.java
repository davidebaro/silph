package it.uniroma3.bar.silph.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.bar.silph.model.Album;
import it.uniroma3.bar.silph.model.Photo;
import it.uniroma3.bar.silph.model.Request;

public interface PhotoRepository extends CrudRepository<Photo, Long>{
	public List<Photo> findByAlbum(Album album);
	public List<Photo> findByRequests(List<Request> requests);
}
