package ar.edu.unnoba.poo.practica4.controls;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ar.edu.unnoba.poo.practica4.entities.Event;
import ar.edu.unnoba.poo.practica4.entities.User;
import ar.edu.unnoba.poo.practica4.repositories.EventRepository;
import ar.edu.unnoba.poo.practica4.services.EventService;
import ar.edu.unnoba.poo.practica4.services.UserService;
import java.lang.Iterable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
@Controller
public class EventController {
      @Autowired
    public EventController(EventRepository eventRepository) {
        this.eventService = new EventService();
		this.eventRepository = eventRepository;
    }
    @Autowired  
    private final EventRepository eventRepository;
    @Autowired
    private final EventService eventService;
    @Autowired
	private UserService userService;
	
	@GetMapping("/events/")
	public List<Event> findAllEventByOwnerID(@RequestParam(value="owner") Long userID){
		return eventService.findAllEventByOwnerID(userID);
	}
	
	@PostMapping("/events")
	public String addUser(@RequestBody Event event) {
		User u = userService.getUser(event.getOwner().getId());
		event.setOwner(u);
		eventService.addEvent(event);
		return "event/myevents";
	}

    @GetMapping("/events")
    public String index(Model model) {
    	Iterable<Event> events = eventRepository.findAll();
    	if(!events.iterator(). hasNext())
    		events = null;
    	model.addAttribute("events", events);
        return "event/events";
    }
    
    @GetMapping("/events/myevents")
	public String findAllMyEvents(Model model) {
		
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails loggedUser = (UserDetails) principal;
		String username = loggedUser.getUsername();
		User user = userService.findUserByUsername(username);
		
		List<Event> events = eventService.findAllEventByOwnerID(user.getId());
		model.addAttribute("events", events);
		return "event/myevents";
	}
    
    @GetMapping("/events/signup")
    public String showSignUpForm(Event event) {
        return "event/add-event";
    }
    
    
    @PostMapping("/events/addevent")
    public String addEvent(@Valid Event event, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "error/error";
	    }
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails loggedUser = (UserDetails) principal;
		String username = loggedUser.getUsername();
		User user = userService.findUserByUsername(username);
		event.setOwner(user);
		eventService.addEvent(event);
		return "event/myevents";
	}
   // @PostMapping("/events/addevent")
   // public String addEvent2(@Valid Event event, BindingResult result, Model model) {
    //     if (result.hasErrors()) {
    //      return "add-event";
    //  }
        
    //  eventRepository.save(event);
    //  model.addAttribute("events", eventRepository.findAll());
    //  return "events";
    //}
    
    
    @GetMapping("/events/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails loggedUser = (UserDetails) principal;
		String username = loggedUser.getUsername();
		User user = userService.findUserByUsername(username);
		long a=user.getId();
    	Event event = eventRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
    	if (event.getOwner().getId()==a){
    		model.addAttribute("event", event);
            return "event/update-event";
    	}
    	else return "error/error";
    }
    
  

	/*@PostMapping("/events/update/{id}")
    public void replaceEvent(@RequestBody Event event ,Model model, @PathVariable Long id, @RequestParam(value="owner") Long userID) {
		if(eventService.getEvent(id).getOwner().getId() == userID) {
			eventService.replaceEvent(event, id);
			model.addAttribute("events", eventRepository.findAll());
			 
		}
	}
	*/
	 @RequestMapping(method=RequestMethod.POST,value="/events/update/{id}")
	    public String updateEvent(@PathVariable("id") long id, @Valid Event event, BindingResult result, Model model) {
	        if (result.hasErrors()) {
	            event.setId(id);
	            return "event/update-event";
	        }
	        eventService.replaceEvent(event,id);
	        model.addAttribute("events", eventRepository.findAll());
	        return "event/myevents";
	    
		
	 }
	
    /*public String updateEvent(@PathVariable("id") long id, @Valid Event event, BindingResult result, Model model,@RequestParam(value="owner") Long userID) {
       if (result.hasErrors()) {
      	event.setId(id);
      		return "update-event";
        }
       if(eventService.getEvent(id).getOwner().getId() == userID) {
    	   eventService.replaceEvent(event,id);
    	   model.addAttribute("events", eventRepository.findAll());
        }
       return "events";
       }*/
   // @PutMapping("/events/update/{id}")	
//	public void replaceEvent(@RequestBody Event event , @PathVariable Long id, @RequestParam(value="owner") Long userID) {
	//	if(eventService.getEvent(id).getOwner().getId() == userID) {
		//	eventService.replaceEvent(event , id);
		//}
	//}
    
    @GetMapping("/events/delete/{id}")
    public String deleteEvent(@PathVariable("id") long id, Model model) {
    	Event event = eventRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
    	eventRepository.delete(event);
        model.addAttribute("events", eventRepository.findAll());
        return "event/events";
    }
    @InitBinder
    public void initBinder(final WebDataBinder binder) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}