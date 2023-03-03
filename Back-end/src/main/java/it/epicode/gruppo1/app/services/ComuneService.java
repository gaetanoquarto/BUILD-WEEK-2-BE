package it.epicode.gruppo1.app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.epicode.gruppo1.app.entities.Comune;
import it.epicode.gruppo1.app.entities.Provincia;
import it.epicode.gruppo1.app.repositories.ComuneRepo;

@Service
public class ComuneService {
	
	@Autowired
	private ComuneRepo cr;
	
	public Comune save(Comune c) {
		return cr.save(c);
	}
	
	public Optional<Comune> getById(int id) {
		return cr.findById(id);
	}
	
	public List<Comune> getAll() {
		return cr.findAll();
	}
	
	public Page<Comune> getAll_page(Pageable pageable) {
		return cr.findAll(pageable);
	}
	
	public void delete(Comune c) {
		cr.delete(c);
	}
	
	public List<Comune> saveAll(List<Comune> c) {
		return cr.saveAll(c);
	}

}
