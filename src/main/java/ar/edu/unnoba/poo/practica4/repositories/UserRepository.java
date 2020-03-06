package ar.edu.unnoba.poo.practica4.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ar.edu.unnoba.poo.practica4.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query("select u from User u where u.username = :username")
	public User findUserByUsername(@Param("username") String username);
	@Query("select username from User")
	public List<String> showUsersbyUsername();
}

