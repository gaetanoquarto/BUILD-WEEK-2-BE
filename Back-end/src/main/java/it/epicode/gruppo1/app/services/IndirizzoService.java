package it.epicode.gruppo1.app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.epicode.gruppo1.app.entities.Indirizzo;
import it.epicode.gruppo1.app.repositories.IndirizzoRepo;

@Service
public class IndirizzoService {
	
	@Autowired
	private IndirizzoRepo ir;
	
	public Indirizzo save(Indirizzo i) {
		return ir.save(i);
	}
	
	public Optional<Indirizzo> getById(int id) {
		return ir.findById(id);
	}
	
	public List<Indirizzo> getAll() {
		return ir.findAll();
	}
	
	public Page<Indirizzo> getAll_page(Pageable pageable) {
		return ir.findAll(pageable);
	}
	
	public void delete(Indirizzo i) {
		ir.delete(i);
	}

}
