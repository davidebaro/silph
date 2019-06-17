package it.uniroma3.bar.silph.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.bar.silph.model.Album;
import it.uniroma3.bar.silph.model.Photographer;
import it.uniroma3.bar.silph.repository.AlbumRepository;


@Service
public class AlbumService {
	@Autowired
	private AlbumRepository albumRepository;
	
	@Transactional
	public List<Album> getAll(){
		return (List<Album>) albumRepository.findAll();
	}
	
	@Transactional
	public Album get(Album album) {
		return albumRepository.findById(album.getId()).get();
	}
	
	@Transactional
	public Album get(Long id) {
		return albumRepository.findById(id).get();
	}
	
	@Transactional
	public Album getByNameAndDescription(Album album) {
		return albumRepository.findByNameAndDescription(album.getName(), album.getDescription());
	}
	
	@Transactional
	public boolean contains(Album album) {
		Album realAlbum = albumRepository.findByNameAndDescription(album.getName(), album.getDescription());
		return realAlbum!=null;
	}
	
	@Transactional
	public Album addAlbum(Album album) {
		return albumRepository.save(album);
	}

	@Transactional
	public List<Album> getFromPhotographer(Photographer photographer) {
		return albumRepository.findByPhotographer(photographer);
	}
}
