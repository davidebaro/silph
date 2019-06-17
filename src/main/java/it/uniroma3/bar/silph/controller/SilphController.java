package it.uniroma3.bar.silph.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.bar.silph.model.Album;
import it.uniroma3.bar.silph.model.Customer;
import it.uniroma3.bar.silph.model.Photo;
import it.uniroma3.bar.silph.model.Photographer;
import it.uniroma3.bar.silph.model.Request;
import it.uniroma3.bar.silph.model.User;
import it.uniroma3.bar.silph.repository.UserRepository;
import it.uniroma3.bar.silph.services.AlbumService;
import it.uniroma3.bar.silph.services.CustomerService;
import it.uniroma3.bar.silph.services.PhotoService;
import it.uniroma3.bar.silph.services.PhotographerService;
import it.uniroma3.bar.silph.services.RequestService;
import it.uniroma3.bar.silph.services.UserService;

@Controller
@SessionAttributes("cart")
public class SilphController {

	@Autowired
	private PhotoService photoService;

	@Autowired
	private UserService userService;

	@Autowired
	private PhotographerService photographerService;

	@Autowired
	private AlbumService albumService;

	@Autowired
	private RequestService requestService;

	@Autowired
	private CustomerService customerService;

	@ModelAttribute("cart")
	public List<Photo> cart(Model model) {
		return new ArrayList<Photo>();
	}

	@RequestMapping(value = {"/", "", "/photos"})
	public String index(Model model) {
		model.addAttribute("photos", photoService.getAllPhotos());
		return "photos.html";
	}

	@RequestMapping(value = "/login")
	public String login(Model model) {
		model.addAttribute("user", new User());
		return "login_form.html";
	}

	@RequestMapping("/manager")
	public String manager(Model model) {
		model.addAttribute("photographers", photographerService.getAll());
		model.addAttribute("photographer", new Photographer());
		return "photographer_manager.html";
	}

	@RequestMapping(value = "/attempt_login", method = RequestMethod.POST)
	public String attemptLogin(@Valid @ModelAttribute("user") User user, Model model){
		try {
			User realUser = userService.getUser(user.getUsername());
			if(realUser.getPassword().equals(user.getPassword())) {
				return manager(model);
			}
			else {
				return "login_form.html";
			}
		}
		catch (NoSuchElementException e) {
			return "login_form.html";
		}
	}

	@RequestMapping(value="/insert_photographer", method = RequestMethod.POST)
	public String insertPhotographer(@Valid @ModelAttribute("photographer") Photographer photographer, Model model) {
		if(!photographerService.contains(photographer)) {
			photographerService.addPhotographer(photographer);
		}
		model.addAttribute("photographers", photographerService.getAll());
		model.addAttribute("photographer", new Photographer());
		return "photographer_manager.html";
	}

	@RequestMapping(value="/select_photographer", method = RequestMethod.GET)
	public String selectPhotographer(@Valid @ModelAttribute("photographer") Photographer photographer, Model model) {
		Photographer realPhotographer = photographerService.get(photographer);
		model.addAttribute("albums", albumService.getFromPhotographer(realPhotographer)); // da cambiare
		Album album = new Album();
		album.setPhotographer(realPhotographer);
		model.addAttribute("album", album);
		return "album_manager.html";
	}

	@RequestMapping(value="/insert_album/{photographer_id}", method = RequestMethod.POST)
	public String insertAlbum(@PathVariable("photographer_id") Long photographerId, @Valid @ModelAttribute("album") Album album, Model model) {
		Photographer photographer = photographerService.get(photographerId);
		if(!albumService.contains(album)) {
			album.setPhotographer(photographer);
			albumService.addAlbum(album);
		}
		album = new Album();
		album.setPhotographer(photographer);
		model.addAttribute("album", album);
		model.addAttribute("albums", albumService.getFromPhotographer(photographer));
		return "album_manager.html";
	}

	@RequestMapping(value="/select_album", method = RequestMethod.GET)
	public String selectAlbum(@Valid @ModelAttribute("album") Album album, Model model) {
		Album realAlbum = albumService.get(album);
		Photo photo = new Photo();
		photo.setAlbum(realAlbum);
		model.addAttribute("photo", photo);
		return "photo_manager.html";
	}

	@RequestMapping(value="insert_photo/{album_id}", method=RequestMethod.POST)
	public String insertPhoto(@PathVariable("album_id") Long albumId, @RequestParam("file") MultipartFile file, Model model) {
		Photo photo = new Photo();
		Album album = albumService.get(albumId);
		photo.setAlbum(album);
		try {
			photo.setPic(file.getBytes()); 
			photo.setAlbum(album);
			photoService.add(photo);
		}
		catch (IOException e){

		}

		/*
		Album album = albumService.get(albumId);
		photo.setAlbum(album);
		photoService.add(photo);*/
		photo = new Photo();
		photo.setAlbum(album);
		model.addAttribute("photo", photo);
		return "photo_manager.html";
	}

	@GetMapping(value="/photo/{photo_id}", produces = MediaType.IMAGE_JPEG_VALUE)
	public @ResponseBody byte[] getPhoto(@PathVariable("photo_id") Long id) {
		Photo photo = photoService.get(id);
		return photo.getPic();
	}

	@RequestMapping("/view/{photo_id}") 
	public String view(@PathVariable("photo_id") Long id, Model model){
		Photo photo = photoService.get(id);
		model.addAttribute("photo", photo);
		return "view_image.html";
	}

	@RequestMapping("/add_to_cart/{photo_id}")
	public String addToCart(@SessionAttribute("cart") List<Photo> cart, @PathVariable("photo_id") Long id, Model model) {
		if (cart==null) {
			cart = new ArrayList<Photo>();
		}
		Photo photo = photoService.get(id);
		cart.add(photo);
		model.addAttribute("cart", cart);
		return showCart(cart, model);
	}

	@RequestMapping("/cart")
	public String showCart(@SessionAttribute("cart") List<Photo> cart, Model model) {
		model.addAttribute("cart", cart);
		return "cart.html";
	}

	@RequestMapping("/remove_from_cart/{photo_id}")
	public String removeFromCart(@PathVariable("photo_id") Long id, @SessionAttribute("cart") List<Photo> cart, Model model) {
		Photo toBeRemoved = new Photo();
		for(Photo elem : cart) {
			if(elem.getId().equals(id)) {
				toBeRemoved = elem;
			}
		}
		cart.remove(toBeRemoved);
		model.addAttribute("cart", cart);
		return "cart.html";
	}

	@RequestMapping("/convalidate_cart")
	public String convalidateCart(Model model) {
		Customer customer = new Customer();
		model.addAttribute("customer", customer);
		return "request_form.html";
	}

	@RequestMapping("/send_form")
	public String sendForm(@SessionAttribute("cart") List<Photo> cart, @ModelAttribute("customer") Customer customer, Model model) {
		Request request = new Request();
		customer = customerService.add(customer);
		request.setCustomer(customer);
		request.setPhotos(cart);

		cart = photoService.get(requestService.get(cart));
		for(Photo p : cart) {
			List<Request> requests = p.getRequests();
			if(requests==null) {
				requests = (new LinkedList<Request>());
			}
			requests.add(request);
		}
		requestService.add(request);
		model.addAttribute("request", request);
		return "request_submitted.html";
	}


	@RequestMapping("/requests")
	public String requests(Model model) {
		List<Request> requests = requestService.getNotHandled();
		model.addAttribute("requests", requests);
		model.addAttribute("handled", false);
		return "requests.html";
	}

	@RequestMapping("/requests_handled")
	public String requestsHandled(Model model) {
		List<Request> requests = requestService.getHandled();
		model.addAttribute("requests", requests);
		model.addAttribute("handled", true);
		return "requests.html";
	}

	@RequestMapping("/request/{request_id}")
	public String request(@PathVariable("request_id") Long id, Model model) {
		Request request = requestService.get(id);
		List<Request> requests = new ArrayList<Request>();
		requests.add(request);
		List<Photo> photos = photoService.get(requests);
		model.addAttribute("photos", photos);
		model.addAttribute("request", request);
		return "view_request.html";
	}

	@RequestMapping("/manage_request/{request_id}")
	public String manageRequest(@PathVariable("request_id") Long id, Model model) {
		Request request = requestService.get(id);
		request.setHandled(true);
		return requests(model);
	}

	@RequestMapping("/photographers")
	public String photographers(Model model) {
		List<Photographer> photographers = photographerService.getAll();
		model.addAttribute("photographers", photographers);
		return "photographers.html";
	}

	@RequestMapping("/albums/{photographer_id}")
	public String albumsByPhotographer(@PathVariable("photographer_id") Long id, Model model) {
		Photographer photographer = photographerService.get(id);
		List<Album> albums = albumService.getFromPhotographer(photographer);
		model.addAttribute("albums", albums);
		model.addAttribute("photographer", photographer);
		return "albums.html";
	}

	@RequestMapping("/albums")
	public String albums(Model model) {
		List<Album> albums = albumService.getAll();
		model.addAttribute("albums", albums);
		return "albums.html";
	}

	@RequestMapping("/photos/{album_id}")
	public String photosByAlbum(@PathVariable("album_id") Long id, Model model) {
		Album album = albumService.get(id);
		List<Photo> photos = photoService.getByAlbum(album);
		model.addAttribute("album", album);
		model.addAttribute("photos", photos);
		return "photos.html";
	}
}
