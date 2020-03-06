package ar.edu.unnoba.poo.practica4.services;

import ar.edu.unnoba.poo.practica4.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

import ar.edu.unnoba.poo.practica4.entities.User;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public List<User> retrieveAllUsers() {
		return userRepository.findAll();
	}
	
	public void addUser(User user) {
	    userRepository.save(user);
	}
	
	public User getUser(Long id) {
	    return userRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
	}
	
	public User replaceUser(User user, Long id) {
	    return userRepository.findById(id)
	      .map(u -> {
	    	  String  a=u.getPassword();
	    	  String r=u.getRole();
	    	u.setUsername(user.getUsername());
	    	user.setPassword(a);
		    user.setRole(r);
	        u.setEmail(user.getEmail());
	        u.setFirstName(user.getFirstName());
	        u.setLastName(user.getLastName());
	        return userRepository.save(u);
	      })
	      .orElseGet(() -> {
	    	  String  a=user.getPassword();
	    	  String r=user.getRole();
	    	  user.setPassword(a);
		    user.setRole(r);
	        return userRepository.save(user);
	      });
	}

	public void deleteUser(@PathVariable Long id) {
		userRepository.deleteById(id);
	}
	
	public User findUserByUsername(String username) {
		return userRepository.findUserByUsername(username);
	}
	public String showUsersbyUsername() {
		return toString();
	}
}
