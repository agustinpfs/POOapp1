package ar.edu.unnoba.poo.practica4.services;

import java.util.List;

import ar.edu.unnoba.poo.practica4.repositories.InviteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

import ar.edu.unnoba.poo.practica4.entities.Invite;
import ar.edu.unnoba.poo.practica4.entities.User;

@Service
public class InviteService {
    @Autowired
    private InviteRepository inviteRepository;
    
    public List<Invite> retrieveAllInvites() {
		return inviteRepository.findAll();
    }

    public Invite replaceInvite(Invite invite, Long id){
      return inviteRepository.findById(id)
      .map(i -> {
        i.setEvent(invite.getEvent());
        i.setUser(invite.getUser());
        return inviteRepository.save(i);
      })
      .orElseGet(() -> {
        return inviteRepository.save(invite);
      });
    }
    
    public void addInvite(Invite invite) {
	    inviteRepository.save(invite);
    }
    
    public Invite getInvite(Long id) {
	    return inviteRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
    }
    
    
    public void deleteInvite(@PathVariable Long id) {
		  inviteRepository.deleteById(id);
    }
    
    public List<Invite> findInviteByUser (User user) {
      return inviteRepository.findAllInviteByUser(user);
	}
}