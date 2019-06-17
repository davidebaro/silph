package it.uniroma3.bar.silph.services;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.bar.silph.model.Photo;
import it.uniroma3.bar.silph.model.Request;
import it.uniroma3.bar.silph.repository.RequestRepository;

@Service
public class RequestService {
	@Autowired
	private RequestRepository requestRepository;
	
	@Transactional
	public Request add(Request request) {
		return requestRepository.save(request);
	}
	
	@Transactional
	public List<Request> getAll() {
		return (List<Request>) requestRepository.findAll();
	}
	
	@Transactional
	public List<Request> get(List<Photo> photos) {
		return requestRepository.findByPhotos(photos);
	}
	
	@Transactional
	public Request get(Long id) {
		return requestRepository.findById(id).get();
	}

	public List<Request> getHandled() {
		List<Request> requests = (List<Request>) requestRepository.findAll();
		List<Request> res = new LinkedList<Request>();
		for(Request r : requests) {
			if(r.isHandled()) {
				res.add(r);
			}
		}
		return res;
	}

	public List<Request> getNotHandled() {
		List<Request> requests = (List<Request>) requestRepository.findAll();
		List<Request> res = new LinkedList<Request>();
		for(Request r : requests) {
			if(!r.isHandled()) {
				res.add(r);
			}
		}
		return res;
	}
}
