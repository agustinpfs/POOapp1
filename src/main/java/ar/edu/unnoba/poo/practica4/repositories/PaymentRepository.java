package ar.edu.unnoba.poo.practica4.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ar.edu.unnoba.poo.practica4.entities.Payment;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@SuppressWarnings("unused")
public interface PaymentRepository extends JpaRepository<Payment, Long> {
	
	//@Query("select p from Payment p where p.numbercard = :numbercard")
	//public Payment findPaymentByCardNumber(@Param("numbercard") String numbercard); 
	
}