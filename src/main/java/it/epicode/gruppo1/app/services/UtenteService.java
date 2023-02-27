package it.epicode.gruppo1.app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.epicode.gruppo1.app.entities.Utente;
import it.epicode.gruppo1.app.repositories.UtenteRepo;

@Service
public class UtenteService {

	@Autowired
	private UtenteRepo ur;
	
	public Utente save(Utente u) {
		return ur.save(u);
	}
	
	public Optional<Utente> getById(int id) {
		return ur.findById(id);
	}
	
	public List<Utente> getAll() {
		return ur.findAll();
	}
	
	public Page<Utente> getAll_page(Pageable pageable) {
		return ur.findAll(pageable);
	}
	
	public void delete(Utente u) {
		ur.delete(u);
	}
	
}
