package ar.edu.unnoba.poo.practica4.controls;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ar.edu.unnoba.poo.practica4.entities.User;
import ar.edu.unnoba.poo.practica4.repositories.UserRepository;
import ar.edu.unnoba.poo.practica4.services.UserService;
import java.lang.Iterable;

@Controller
public class UserController {
    
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userService = new UserService();
		this.userRepository = userRepository;
    }
    @Autowired
    private final UserService userService;
    @GetMapping("/users")
    public String index(Model model) {
    	Iterable<User> users = userRepository.findAll();
    	if(!users.iterator(). hasNext())
    		users = null;
    	model.addAttribute("users", users);
        return "user/users";
    }
    
    @GetMapping("/users/signup")
    public String showSignUpForm(User user) {
        return "user/add-user";
    }
    
    @PostMapping("/users/adduser")
    public String addUser(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/add-user";
        }
        user.setRole("USER");
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
        model.addAttribute("users", userRepository.findAll());
        return "user/users";
    }
    
    @GetMapping("/users/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);
        
        return "user/update-user";
    }
    @RequestMapping(method=RequestMethod.POST,value="/users/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            user.setId(id);
            return "user/update-user";
        }
        
        userService.replaceUser(user,id);
        model.addAttribute("users", userRepository.findAll());
        return "user/users";
    }
    
    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {    	
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
        model.addAttribute("users", userRepository.findAll());
        return "user/users";
    }
}