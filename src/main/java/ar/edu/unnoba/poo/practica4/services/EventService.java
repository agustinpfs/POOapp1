package ar.edu.unnoba.poo.practica4.services;
import java.util.List;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;

import ar.edu.unnoba.poo.practica4.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import ar.edu.unnoba.poo.practica4.entities.Event;
import ar.edu.unnoba.poo.practica4.entities.User;

@SuppressWarnings("unused")
@Service
public class EventService {

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private UserService userService;
	
	public List<Event> findAllEventByOwnerID(Long userID){
		return eventRepository.findAllEventByOwner(userService.getUser(userID));
	}
	public Iterable<Event> retrieveAllEvents() {
		return eventRepository.findAll();
	}
	public void addEvent(Event event) {
		eventRepository.save(event);
	}
	
	public Event replaceEvent(Event event, Long id) {
	     return eventRepository.findById(id)
	     .map(e -> {
	      e.setEventDate(event.getEventDate());
	      e.setEventName(event.getEventName());
	      e.setStartRegistrations(event.getStartRegistrations());
	      e.setEndRegistrations(event.getEndRegistrations());
	      e.setCapacity(event.getCapacity());
	      e.setCost(event.getCost());
	      e.setPrivateEvent(event.isPrivateEvent());
	      e.setPlace(event.getPlace());
	      //e.setAvailable(event.isAvailable());
	      return eventRepository.save(e);
	     })
	     .orElseGet(() -> {
	       return eventRepository.save(event);
	     });
	   }
	
	public void deleteEvent(@PathVariable Long id) {
		eventRepository.deleteById(id);
	}
	
	public Event getEvent(Long id) {
	    return eventRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
	}

	
}