package ar.edu.unnoba.poo.practica4.services;

import java.util.List;

import ar.edu.unnoba.poo.practica4.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

import ar.edu.unnoba.poo.practica4.entities.Payment;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    
    public List<Payment> retrieveAllPayment() {
		return paymentRepository.findAll();
    }
    
    public void addPayment (Payment payment) {
	    paymentRepository.save(payment);
    }
    
    public Payment getPayment (Long id) {
	    return paymentRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
    }
    
    public Payment replacePayment(Payment payment, Long id) {
	    return paymentRepository.findById(id)
	      .map(i -> {
	        i.setRegistration(payment.getRegistration());
	        i.setCard(payment.getCard());
	        i.setCardNumber(payment.getCardNumber());
	        return paymentRepository.save(i);
	      })
	      .orElseGet(() -> {
	        return paymentRepository.save(payment);
	      });
    }
    
    public void deletePayment(@PathVariable Long id) {
		paymentRepository.deleteById(id);
    }
    
  // public Payment findPaymentByCardNumber(String cardnumber) {
	//   return paymentRepository.findPaymentByCardNumber(cardnumber);
	//}
}