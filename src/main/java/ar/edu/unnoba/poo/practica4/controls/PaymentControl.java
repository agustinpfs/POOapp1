package ar.edu.unnoba.poo.practica4.controls;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import ar.edu.unnoba.poo.practica4.services.EventService;
import ar.edu.unnoba.poo.practica4.services.PaymentService;
import ar.edu.unnoba.poo.practica4.services.UserService;
import ar.edu.unnoba.poo.practica4.entities.Event;
import ar.edu.unnoba.poo.practica4.entities.Payment;
import ar.edu.unnoba.poo.practica4.entities.User;

@RestController
public class PaymentControl {

	@Autowired
	private PaymentService paymentService;
	
	
	@GetMapping("/payments")
	public List<Payment> retrieveAllPayments() {
		return paymentService.retrieveAllPayment();
	}
	
	

    /*@GetMapping("/{id}/delete")
    public String delete(@PathVariable ("id") Long id){
        userService.delete(id);
        return "redirect:/users";
    }
	
	
	
	//@GetMapping("/payments/")
	//public Payment findPaymentByCardNumber(@RequestParam(value="cardNumber") String number){
		//return paymentService.findPaymentByCardNumber(number);
	//}
	
/*	@PostMapping("/payments")
	public void addPayment(@RequestBody Payment payment) {
		paymentService.addPayment(payment);
	}
	
	@GetMapping("/payments/{id}")
	public Payment getPayment(@PathVariable Long id) {
	    return paymentService.getPayment(id);
	}
	
	@PutMapping("/payments/{id}")
	public Payment replaceUser(@RequestBody Payment payment, @PathVariable Long id) {
	    return paymentService.replacePayment(payment, id);
	}

	  @DeleteMapping("/payments/{id}")
	  void deletePayment(@PathVariable Long id) {
		  paymentService.deletePayment(id);
	  }
	  */
}
