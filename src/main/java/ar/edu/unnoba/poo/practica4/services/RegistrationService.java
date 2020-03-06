package ar.edu.unnoba.poo.practica4.services;

import java.util.List;

import ar.edu.unnoba.poo.practica4.repositories.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

import ar.edu.unnoba.poo.practica4.entities.Registration;

@Service
public class RegistrationService {

    @Autowired
    private RegistrationRepository registrationRepository;
    
    public List<Registration> retrieveAllRegistrations() {
		return registrationRepository.findAll();
    }
    
    public void addRegistration(Registration registration) {
	    registrationRepository.save(registration);
    }
    
    public Registration getRegistration (Long id) {
	    return registrationRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
    }
    
    public Registration replaceRegistration(Registration registration, Long id) {
	    return registrationRepository.findById(id)
	      .map(r -> {
	        r.setUser(registration.getUser());
	        r.setEvent(registration.getEvent());
	        r.setCreatedAt(registration.getCreatedAt());
	        return registrationRepository.save(r);
	      })
	      .orElseGet(() -> {
	        return registrationRepository.save(registration);
	      });
    }
    
    public void deleteRegistration(@PathVariable Long id) {
		registrationRepository.deleteById(id);
	}
	
	//public Registration findRegistrationById(Long id) {
	//	return registrationRepository.findRegistrationByID(id);
	//}
}