package ar.edu.unnoba.poo.practica4.controls;

import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import ar.edu.unnoba.poo.practica4.services.EventService;
import ar.edu.unnoba.poo.practica4.services.InviteService;
import ar.edu.unnoba.poo.practica4.services.UserService;
import ar.edu.unnoba.poo.practica4.entities.Event;
import ar.edu.unnoba.poo.practica4.entities.Invite;
import ar.edu.unnoba.poo.practica4.entities.User;
import ar.edu.unnoba.poo.practica4.repositories.EventRepository;

@RestController
public class InviteControl {
	@Autowired
	private InviteService inviteService;
	@Autowired
	private UserService userService;
	@Autowired
	private EventService eventService;
	@Autowired  
    private  EventRepository eventRepository;
	
	@GetMapping("/invites/myinvites")
	public String  findAllMyInvites(Model model, BindingResult result) {
		if (result.hasErrors()) {
			return "error/error";
	    }
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails loggedUser = (UserDetails) principal;
		String username = loggedUser.getUsername();
		User user = userService.findUserByUsername(username);
		
		List<Invite> invites = inviteService.findInviteByUser(user);
		model.addAttribute("invites", invites);
		return "invite/invites";
	}
	
	@GetMapping("invites/sendi/{eventId}/{userId}")
    public String sendInv(Model model, @PathVariable Long eventId, @PathVariable Long userId){
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails loggedUser = (UserDetails) principal;
		String username = loggedUser.getUsername();
		User user = userService.findUserByUsername(username);
		Event event = eventService.getEvent(eventId);
        if (event.getOwner().getId()==user.getId()) {
        	Invite i = new Invite();
            i.setEvent(event);
            i.setUser(userService.getUser(userId));
            inviteService.addInvite(i);
            return "/invite/invite/{eventId}";
            }
        else
            return "/error/error";
        }
	
	
	@GetMapping("/invites/invite/{id}")
	public String invite(Model model, @PathVariable Long id) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails loggedUser = (UserDetails) principal;
		String username = loggedUser.getUsername();
		User user = userService.findUserByUsername(username);
		long a=user.getId();
		if (Objects.equals(eventService.getEvent(id).getOwner().getId(), a)) {
            List<User> users = userService.retrieveAllUsers();
            model.addAttribute("users", users);
            model.addAttribute("eventid", id);
            return "invite/invite-users";}
		else return "/error/error";
		
	}
		
		
		
		
		
		
		
	/*	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails loggedUser = (UserDetails) principal;
		String username = loggedUser.getUsername();
		User user = userService.findUserByUsername(username);
		long a=user.getId();
		Event event = eventRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid event Id:" + id));    
		if (eventService.getEvent(id).getOwner().getId()==a) {
			Iterable<User> users = userService.retrieveAllUsers();
	        model.addAttribute("users", users);
	        model.addAttribute("event", event);
	        return "/invites/invite-users";
	        }
		else
			return "error/error";
	}*/
	
	@GetMapping("/invites/delete/{inviteId}")
    public String delete(Model model, @PathVariable Long inviteId, HttpServletRequest request){
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails loggedUser = (UserDetails) principal;
		String username = loggedUser.getUsername();
		User user = userService.findUserByUsername(username);
		Invite inv = inviteService.getInvite(inviteId);
        if ((user.getId() == inv.getUser().getId()) || (user.getId() == inv.getEvent().getOwner().getId())) {
                inviteService.deleteInvite(inviteId);
           return "/events/myEvents";}
        else
        	   return "error/error";
            }
	@GetMapping("/invites")
		public List<Invite> retrieveAllInvites(Model model) {
		List<Invite> invites = inviteService.retrieveAllInvites();
		model.addAttribute("invites", invites);
		return invites;
		}
	/*	
	@PostMapping("/invites")
	public void addInvite(@RequestBody Invite invite) {
		inviteService.addInvite(invite);
	}
	
	@GetMapping("/invites/{id}")
	public Invite getInvite(@PathVariable Long id) {
	    return inviteService.getInvite(id);
	}
	
	@PutMapping("/invites/{id}")
	public Invite replaceInvite(@RequestBody Invite invite, @PathVariable Long id) {
	  return inviteService.replaceInvite(invite, id);
	}
	
	@GetMapping("/invite/")
	public List<Invite> findAllInviteByUser(@RequestParam(value="user") User userID){
		return inviteService.findInviteByUser(userID);
	}

	  @DeleteMapping("/invites/{id}")
	  void deleteInvite(@PathVariable Long id) {
		  inviteService.deleteInvite(id);
	  }¨*/
}
