package ar.edu.unnoba.poo.practica4.controls;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import ar.edu.unnoba.poo.practica4.services.RegistrationService;

import ar.edu.unnoba.poo.practica4.entities.Registration;

@RestController
public class RegistrationControl {

	@Autowired
	private RegistrationService registrationService;
	
	@GetMapping("/resgistrations")
	public List<Registration> retrieveAllRegistrations() {
		return registrationService.retrieveAllRegistrations();
	}
	
	@PostMapping("/registrations")
	public void addRegistration(@RequestBody Registration registration) {
		registrationService.addRegistration(registration);
	}
	
	@GetMapping("/registrations/{id}")
	public Registration getRegistration(@PathVariable Long id) {
	    return registrationService.getRegistration(id);
	}
	
	@PutMapping("/registrations/{id}")
	public Registration replaceRegistration(@RequestBody Registration registration, @PathVariable Long id) {
	    return registrationService.replaceRegistration(registration, id);
	}

	  @DeleteMapping("/registrations/{id}")
	  void deleteRegistration(@PathVariable Long id) {
		  registrationService.deleteRegistration(id);
	  }
}
