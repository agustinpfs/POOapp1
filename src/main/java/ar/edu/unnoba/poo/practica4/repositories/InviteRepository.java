package ar.edu.unnoba.poo.practica4.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.unnoba.poo.practica4.entities.Invite;
import ar.edu.unnoba.poo.practica4.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InviteRepository extends JpaRepository<Invite, Long> {
	
	@Query("select i from Invite i where i.user = :user")
	public List<Invite> findAllInviteByUser(@Param("user") User user); 
	
}