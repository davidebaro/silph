package it.uniroma3.bar.silph.services;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.bar.silph.model.Album;
import it.uniroma3.bar.silph.model.Photo;
import it.uniroma3.bar.silph.model.Request;
import it.uniroma3.bar.silph.repository.PhotoRepository;


@Service
public class PhotoService {
	
	@Autowired
	private PhotoRepository photoRepository;

	@Transactional
	public List<Photo> getAllPhotos() {
		return (List<Photo>) photoRepository.findAll();
	}
	
	@Transactional 
	public Photo add(Photo photo) {
		return photoRepository.save(photo);
	}
	
	@Transactional
	public Photo get(Long id) {
		return photoRepository.findById(id).get();
	}
	
	@Transactional
	public List<Photo> get(List<Request> requests) {
		return photoRepository.findByRequests(requests);
	}

	@Transactional
	public List<Photo> getByAlbum(Album album) {
		return photoRepository.findByAlbum(album);
	}
}
