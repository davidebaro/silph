package it.uniroma3.bar.silph.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.bar.silph.model.Album;
import it.uniroma3.bar.silph.model.Photographer;

public interface AlbumRepository extends CrudRepository<Album, Long>{
	public Album findByNameAndDescription(String name, String description);

	public List<Album> findByPhotographer(Photographer photographer);
}
